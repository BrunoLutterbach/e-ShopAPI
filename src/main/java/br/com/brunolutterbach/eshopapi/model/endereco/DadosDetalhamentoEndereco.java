package br.com.brunolutterbach.eshopapi.model.endereco;

import lombok.Builder;

@Builder
public record DadosDetalhamentoEndereco(
        Long id,
        String logradouro,
        String cep,
        String numero,
        String cidade,
        Boolean principal) {

    public DadosDetalhamentoEndereco(Endereco endereco) {
        this(endereco.getId(), endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(), endereco.getCidade(), endereco.getPrincipal());
    }
}
