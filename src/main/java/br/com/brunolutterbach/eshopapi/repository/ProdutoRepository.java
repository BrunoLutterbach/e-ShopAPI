package br.com.brunolutterbach.eshopapi.repository;

import br.com.brunolutterbach.eshopapi.model.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
