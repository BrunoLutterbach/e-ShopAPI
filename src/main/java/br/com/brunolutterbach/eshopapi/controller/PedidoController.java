package br.com.brunolutterbach.eshopapi.controller;

import br.com.brunolutterbach.eshopapi.model.carrinho.Carrinho;
import br.com.brunolutterbach.eshopapi.model.enums.StatusPedido;
import br.com.brunolutterbach.eshopapi.model.item.Item;
import br.com.brunolutterbach.eshopapi.model.paypal.DadosLinkPagamento;
import br.com.brunolutterbach.eshopapi.model.pedido.DadosPedidoCriado;
import br.com.brunolutterbach.eshopapi.model.pedido.DadosPedidosUsuario;
import br.com.brunolutterbach.eshopapi.model.pedido.ItemPedido;
import br.com.brunolutterbach.eshopapi.model.pedido.Pedido;
import br.com.brunolutterbach.eshopapi.repository.EnderecoRepository;
import br.com.brunolutterbach.eshopapi.service.PayPalService;
import br.com.brunolutterbach.eshopapi.service.PedidoService;
import br.com.brunolutterbach.eshopapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api/pedido")
@AllArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PedidoController {

    final PedidoService pedidoService;
    final UsuarioService usuarioService;
    final PayPalService payPalService;
    final EnderecoRepository enderecoRepository;

    @PostMapping("/novo")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DadosPedidoCriado> criarPedido(HttpSession session) {
        // Recupera o carrinho da sessão
        var carrinho = (Carrinho) session.getAttribute("carrinho");

        // Verifica se o carrinho não está vazio
        if (carrinho == null || carrinho.getItens().isEmpty()) {
            throw new IllegalArgumentException("Carrinho vazio");
        }

        // Cria um novo pedido e atribui o usuário
        var pedido = new Pedido();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();
        var usuario = usuarioService.buscarUsuarioPorEmail(email);
        pedido.setUsuario(usuario);

        pedido.setEnderecoEntregaId(enderecoRepository.findIdEnderecoPrincipal());

        // Adiciona cada item do carrinho ao pedido
        for (Item item : carrinho.getItens()) {
            var itemPedido = new ItemPedido();
            itemPedido.setProduto(item.getProduto());
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setPrecoUnitario(item.getProduto().getPreco());
            itemPedido.setPedido(pedido);
            itemPedido.setPrecoTotal(itemPedido.getPrecoUnitario().multiply(new BigDecimal(itemPedido.getQuantidade())));
            pedido.getItensPedido().add(itemPedido);
            var valorTotal = BigDecimal.ZERO;
            for (ItemPedido itemPedido1 : pedido.getItensPedido()) {
                valorTotal = valorTotal.add(itemPedido1.getPrecoTotal());
            }
            pedido.setValorTotal(valorTotal);
        }
        carrinho.removerTodosItens();
        pedidoService.salvar(pedido);


        return ResponseEntity.ok(new DadosPedidoCriado(pedido.getId(), pedido.getValorTotal()));
    }

    @PostMapping("/pagar/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<DadosLinkPagamento> pagarPedido(@PathVariable Long id, HttpServletRequest httpServletRequest) throws Exception {
        var pedido = pedidoService.buscarPedidoPorIdVerificandoSePago(id);

        // Obtem as url de retorno e cancelamento
        var returnUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/api/pedido/pagamento-concluido";
        var cancelUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/api/pedido/pagamento-falhou";

        // Cria o pedido no PayPal e retorna url de pagamento
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        var order = payPalService.createOrder(pedido.getValorTotal(), returnUrl, cancelUrl, pedido);
        return ResponseEntity.ok(new DadosLinkPagamento(order));
    }

    @GetMapping("/pagamento-concluido")
    public String pagamentoConcluido(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws Exception {
        // Baixa o pagamento no PayPal
        var payment = payPalService.executePayment(paymentId, payerId);

        // Busca o pedido pelo ID e atualiza o status para PAGO
        var pedido = pedidoService.buscarPedidoPorId(parseLong(payment.getTransactions().get(0).getCustom()));
        pedido.setStatus(StatusPedido.PAGO);
        pedido.setDataEntrega(LocalDateTime.now().plusDays(7));
        pedidoService.salvar(pedido);

        return "redirect:/home";
    }

    @GetMapping("/pedidos")
    public ResponseEntity<List<DadosPedidosUsuario>> buscarPedidos() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = authentication.getName();
        var usuario = usuarioService.buscarUsuarioPorEmail(email);
        var pedidos = pedidoService.buscarPedidosPorUsuario(usuario.getId());
        return ResponseEntity.ok(pedidos);
    }

}
