package br.com.brunolutterbach.eshopapi.model.carrinho;

import br.com.brunolutterbach.eshopapi.model.item.Item;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Carrinho {

    private List<Item> itens = new ArrayList<>();
    private BigDecimal ValorTotalCarrinho = BigDecimal.ZERO;
    private int quantidadeItens= 0;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    public void adicionarItem(Item item) {
        itens.add(item);
        ValorTotalCarrinho = ValorTotalCarrinho.add(item.getValorTotal());
        quantidadeItens += item.getQuantidade();
    }

    public void removerItem(Long idProduto) {
        // Filtra o item pelo id do produto para remover
        var item = itens.stream().filter(i -> i.getProduto().getId().equals(idProduto)).findFirst().get();
        itens.remove(item);
        ValorTotalCarrinho = ValorTotalCarrinho.subtract(item.getValorTotal());
        quantidadeItens -= item.getQuantidade();
    }

    public void removerTodosItens() {
        itens.clear();
        ValorTotalCarrinho = BigDecimal.ZERO;
        quantidadeItens = 0;
    }
}
