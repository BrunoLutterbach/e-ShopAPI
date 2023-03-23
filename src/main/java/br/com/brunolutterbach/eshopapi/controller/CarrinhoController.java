package br.com.brunolutterbach.eshopapi.controller;

import br.com.brunolutterbach.eshopapi.model.carrinho.Carrinho;
import br.com.brunolutterbach.eshopapi.model.carrinho.DadosListagemCarrinho;
import br.com.brunolutterbach.eshopapi.model.item.DadosListagemItensCarrinho;
import br.com.brunolutterbach.eshopapi.model.item.Item;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@SecurityRequirement(name = "bearer-key")
public class CarrinhoController {
    @GetMapping("/itens")
    @ResponseBody
    public ResponseEntity<DadosListagemCarrinho> getCarrinhoItens(HttpSession session) {
        // recupera o carrinho da sessão que foi criado no ProdutoController
        var carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }

        List<DadosListagemItensCarrinho> itens = new ArrayList<>();
        for (Item item : carrinho.getItens()) {
            var produto = item.getProduto();
            var dadosItens = new DadosListagemItensCarrinho(
                    produto, item.getQuantidade(), item.getPrecoUnitario(), item.getPrecoTotalItens());
            itens.add(dadosItens);
        }
        return ResponseEntity.ok(new DadosListagemCarrinho(itens, carrinho.getValorTotalCarrinho(), carrinho.getQuantidadeItens()));
    }

    @DeleteMapping("/{id}")
    public String removerItem(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("carrinho") == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Carrinho vazio ou não existe!");
        }
        var carrinho = (Carrinho) session.getAttribute("carrinho");
        carrinho.removerItem(id);
        session.setAttribute("carrinho", carrinho);
        return "redirect:/carrinho";
    }
}
