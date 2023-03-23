package br.com.brunolutterbach.eshopapi.repository;

import br.com.brunolutterbach.eshopapi.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    Optional<Object> findOptionalByEmail(String email);
}
