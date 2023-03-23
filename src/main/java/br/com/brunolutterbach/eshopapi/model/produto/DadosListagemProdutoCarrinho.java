package br.com.brunolutterbach.eshopapi.model.produto;


import java.math.BigDecimal;

public record DadosListagemProdutoCarrinho(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        String categoria,
        String marca
) {
}

