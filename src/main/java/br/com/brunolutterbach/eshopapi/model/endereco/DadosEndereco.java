package br.com.brunolutterbach.eshopapi.model.endereco;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record DadosEndereco(
        @NotBlank
        String logradouro,
        @NotBlank
        String cep,
        @NotBlank
        String numero,
        @NotBlank
        String cidade,
        Boolean principal
) {

    public DadosEndereco(Endereco endereco) {
        this(endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(), endereco.getCidade(), endereco.getPrincipal());
    }
}

