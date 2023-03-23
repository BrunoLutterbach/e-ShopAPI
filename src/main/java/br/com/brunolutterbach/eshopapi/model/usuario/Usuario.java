package br.com.brunolutterbach.eshopapi.model.usuario;

import br.com.brunolutterbach.eshopapi.model.endereco.Endereco;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<Endereco> enderecos;
    private String telefone;
    private LocalDate dataNascimento;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    public Usuario(DadosCadastroUsuario dados) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.nome = dados.nome();
        this.email = dados.email();
        this.senha = encoder.encode(dados.senha());
        if (dados.enderecos() != null) {
            this.enderecos = new ArrayList<>();
            dados.enderecos().forEach(endereco -> this.enderecos.add(new Endereco(endereco)));
            this.enderecos.get(0).setPrincipal(true);  // Primeiro endereço no JSON é setado como principal
        } else {
            this.enderecos = new ArrayList<>();
        }
        this.telefone = dados.telefone();
        this.dataNascimento = dados.dataNascimento();
    }

    public Usuario() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void atualizar(DadosAtualizarUsuario dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
        if (dados.senha() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            this.senha = encoder.encode(dados.senha());
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
        this.dataAtualizacao = LocalDateTime.now();
    }
}