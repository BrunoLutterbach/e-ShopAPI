package br.com.brunolutterbach.eshopapi.model.produto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco = BigDecimal.ZERO;
    private String categoria;
    private String marca;
    @ElementCollection(fetch = FetchType.EAGER) // carregar as imagens junto com o produto
    @Fetch(value = FetchMode.SUBSELECT)
    private List<String> images = new ArrayList<>();
    private LocalDateTime dataCadastro = LocalDateTime.now();
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    public Produto(DadosCadastroProduto dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
        this.categoria = dados.categoria();
        this.marca = dados.marca();
        this.images.addAll(dados.images());
    }

    public Produto(DadosListagemProduto produto) {
        this.id = produto.id();
        this.nome = produto.nome();
        this.descricao = produto.descricao();
        this.preco = produto.preco();
        this.categoria = produto.categoria();
        this.marca = produto.marca();
        this.images = produto.images();
    }

    public void atualizar(DadosAtualizarProduto dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        } else if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        } else if (dados.preco() != null) {
            this.preco = dados.preco();
        } else if (dados.categoria() != null) {
            this.categoria = dados.categoria();
        } else if (dados.marca() != null) {
            this.marca = dados.marca();
        } else if (dados.images() != null) {
            this.images = dados.images();
        }
    }
}
