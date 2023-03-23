package br.com.brunolutterbach.eshopapi.model.item;

import br.com.brunolutterbach.eshopapi.model.produto.DadosListagemProduto;
import br.com.brunolutterbach.eshopapi.model.produto.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Item {
    private Produto produto;
    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotalItens;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    public Item(DadosListagemProduto produto, int quantidade) {
        this.produto = new Produto(produto);
        this.quantidade = quantidade;
        this.precoUnitario = produto.preco();
        this.precoTotalItens = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    @JsonIgnore
    public BigDecimal getValorTotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
