package br.com.brunolutterbach.eshopapi.model.produto;

import java.math.BigDecimal;
import java.util.List;

public record DadosListagemProduto(Long id,
                                   String nome,
                                   String descricao,
                                   BigDecimal preco,
                                   String categoria,
                                   String marca,
                                   List<String> images
) {

    public DadosListagemProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getCategoria(), produto.getMarca(), produto.getImages());
    }
}
