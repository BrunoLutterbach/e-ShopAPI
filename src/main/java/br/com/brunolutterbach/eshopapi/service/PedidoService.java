package br.com.brunolutterbach.eshopapi.service;


import br.com.brunolutterbach.eshopapi.model.enums.StatusPedido;
import br.com.brunolutterbach.eshopapi.model.pedido.DadosPedidosUsuario;
import br.com.brunolutterbach.eshopapi.model.pedido.Pedido;
import br.com.brunolutterbach.eshopapi.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    final PedidoRepository pedidoRepository;

    public void salvar(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public Pedido buscarPedidoPorIdVerificandoSePago(Long id) {
        var pedido = pedidoRepository.getReferenceById(id);
        if (pedido.getStatus() == StatusPedido.PAGO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido já pago");
        }
        return pedido;
    }

    public Pedido buscarPedidoPorPaymentId(String paymentId) {
        return pedidoRepository.findByPaymentId(paymentId);
    }

    public Pedido buscarPedidoPorId(Long id) {
       return pedidoRepository.getReferenceById(id);
    }

    public List<DadosPedidosUsuario> buscarPedidosPorUsuario(Long id) {
        var pedidosByUsuario = pedidoRepository.findPedidosByUsuario(id);
        return pedidosByUsuario.isEmpty() ? Collections.emptyList() : pedidosByUsuario;
    }
}
