package br.com.brunolutterbach.eshopapi.model.usuario;

import br.com.brunolutterbach.eshopapi.model.endereco.DadosDetalhamentoEndereco;

import java.util.List;

public record DadosListagemUsuarioEndereco(Long id,
                                           String nome,
                                           String email,
                                           String telefone,
                                           List<DadosDetalhamentoEndereco> enderecos
                                           ) {
    public DadosListagemUsuarioEndereco(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), usuario.getEnderecos().stream()
                .map(DadosDetalhamentoEndereco::new).toList());
    }
}
