package br.com.brunolutterbach.eshopapi.model.usuario;

import br.com.brunolutterbach.eshopapi.model.endereco.DadosEndereco;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

public record DadosCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String senha,
        // @Pattern(regexp = "^\\(\\d{2}\\)\\s\\d{4}-\\d{4}$", message = "Telefone inválido, formato esperado: (99) 9999-9999")
        String telefone,
        LocalDate dataNascimento,
        @Valid
        List<DadosEndereco> enderecos
) {
}
