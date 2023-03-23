package br.com.brunolutterbach.eshopapi.repository;

import br.com.brunolutterbach.eshopapi.model.pedido.DadosPedidosUsuario;
import br.com.brunolutterbach.eshopapi.model.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Pedido findByPaymentId(String paymentId);

    @Query("SELECT new br.com.brunolutterbach.eshopapi.model.pedido.DadosPedidosUsuario(p.id, p.usuario.nome, p.status, p.metodoPagamento, p.paymentId) FROM Pedido p WHERE p.usuario.id = :id")
    List<DadosPedidosUsuario> findPedidosByUsuario(Long id);
}
