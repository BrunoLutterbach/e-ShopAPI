package br.com.brunolutterbach.eshopapi.repository;

import br.com.brunolutterbach.eshopapi.model.endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    boolean existsByIdAndPrincipal(Long id, boolean principal);

    @Query("SELECT e.id FROM Endereco e WHERE e.principal = true")
    Long findIdEnderecoPrincipal();
}

