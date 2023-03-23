package br.com.brunolutterbach.eshopapi.model.endereco;

import br.com.brunolutterbach.eshopapi.model.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private Boolean principal = false;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public Endereco(DadosEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.cep = endereco.cep();
        this.numero = endereco.numero();
        this.cidade = endereco.cidade();
    }

    public Endereco(DadosCadastroEndereco dados) {
        this.logradouro = dados.logradouro();
        this.cep = dados.cep();
        this.numero = dados.numero();
        this.cidade = dados.cidade();
    }

    public Endereco(DadosCadastroEndereco dados, Usuario usuario) {
        this.logradouro = dados.logradouro();
        this.cep = dados.cep();
        this.numero = dados.numero();
        this.cidade = dados.cidade();
        this.usuario = usuario;
    }

    public void atualizarInformacoes(DadosCadastroEndereco dados) {
        if (dados.logradouro() != null) {
            this.logradouro = dados.logradouro();
        } else if (dados.cep() != null) {
            this.cep = dados.cep();
        } else if (dados.numero() != null) {
            this.numero = dados.numero();
        } else if (dados.cidade() != null) {
            this.cidade = dados.cidade();
        }
    }

    public void tornarEnderecoPrincipal() {
        this.principal = true;
    }
}
