package br.com.brunolutterbach.eshopapi.model.carrinho;

import br.com.brunolutterbach.eshopapi.model.item.DadosListagemItensCarrinho;

import java.math.BigDecimal;
import java.util.List;

public record DadosListagemCarrinho(
        List<DadosListagemItensCarrinho> itens,
        BigDecimal valorTotalCarrinho,
        int quantidadeItens

) {
}
