package br.com.brunolutterbach.eshopapi.model.item;

import br.com.brunolutterbach.eshopapi.model.produto.DadosListagemProdutoCarrinho;
import br.com.brunolutterbach.eshopapi.model.produto.Produto;

import java.math.BigDecimal;

public record DadosListagemItensCarrinho(
        DadosListagemProdutoCarrinho produtoCarrinho,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal precoTotalItens
) {

    public DadosListagemItensCarrinho(Produto produto, int quantidade, BigDecimal precoUnitario, BigDecimal precoTotalItens) {
        this(new DadosListagemProdutoCarrinho(produto.getId(), produto.getNome(), produto.getDescricao(),
                produto.getPreco(), produto.getCategoria(), produto.getMarca()), quantidade, precoUnitario, precoTotalItens);
    }
}

