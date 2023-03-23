package br.com.brunolutterbach.eshopapi.model.pedido;

import br.com.brunolutterbach.eshopapi.model.enums.StatusPedido;

public record DadosPedidosUsuario (
        Long id,
        String nome,
        StatusPedido status,
        String metodoPagamento,
        String paymentId
){
}
