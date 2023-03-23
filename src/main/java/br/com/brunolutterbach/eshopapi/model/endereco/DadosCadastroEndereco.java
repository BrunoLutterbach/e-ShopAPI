package br.com.brunolutterbach.eshopapi.model.endereco;


import javax.validation.constraints.NotBlank;

public record DadosCadastroEndereco(
        @NotBlank
        String logradouro,
        @NotBlank
        String cep,
        @NotBlank
        String numero,
        @NotBlank
        String cidade ) {
}
