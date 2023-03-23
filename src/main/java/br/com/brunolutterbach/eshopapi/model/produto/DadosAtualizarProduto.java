package br.com.brunolutterbach.eshopapi.model.produto;

import java.math.BigDecimal;
import java.util.List;

public record DadosAtualizarProduto(
    String nome,
    String descricao,
    BigDecimal preco,
    String categoria,
    String marca,
    List<String> images

) {
}
