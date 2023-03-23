package br.com.brunolutterbach.eshopapi.model.pedido;

import java.math.BigDecimal;

public record DadosPedidoCriado(Long id, BigDecimal valorTotal
                                ) {
}
