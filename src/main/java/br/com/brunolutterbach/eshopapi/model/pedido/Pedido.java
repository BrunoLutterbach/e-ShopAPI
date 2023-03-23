package br.com.brunolutterbach.eshopapi.model.pedido;

import br.com.brunolutterbach.eshopapi.model.carrinho.Carrinho;
import br.com.brunolutterbach.eshopapi.model.enums.StatusPedido;
import br.com.brunolutterbach.eshopapi.model.usuario.Usuario;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itensPedido = new ArrayList<>();
    @Transient // Não será persistido no banco de dados
    private Carrinho carrinho;
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDateTime dataPedido = LocalDateTime.now();
    private LocalDateTime dataEntrega;
    private Long enderecoEntregaId;
    private String metodoPagamento = "Paypal";
    private String paymentId;
    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.PENDENTE;
}
