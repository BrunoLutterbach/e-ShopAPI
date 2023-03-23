package br.com.brunolutterbach.eshopapi.service;

import br.com.brunolutterbach.eshopapi.model.produto.DadosListagemProduto;
import br.com.brunolutterbach.eshopapi.model.usuario.*;
import br.com.brunolutterbach.eshopapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    final UsuarioRepository usuarioRepository;

    public DadosListagemUsuario cadastrar(DadosCadastroUsuario dados) {
        var usuario = new Usuario(dados);
        usuario.getEnderecos().forEach(endereco -> endereco.setUsuario(usuario));
        usuarioRepository.save(usuario);
        return new DadosListagemUsuario(usuario);
    }

    public DadosListagemUsuario buscarPorId(Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        return new DadosListagemUsuario(usuario);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.getReferenceById(id);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    public Page<DadosListagemUsuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(DadosListagemUsuario::new);
    }

    public DadosListagemUsuarioEndereco listarEnderecos(Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        return new DadosListagemUsuarioEndereco(usuario);
    }

    public DadosListagemUsuario atualizarInformacoes(Long id, DadosAtualizarUsuario dados) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.atualizar(dados);
        usuarioRepository.save(usuario);
        return new DadosListagemUsuario(usuario);
    }

    public void remover(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
        throw new IllegalArgumentException("Usuário não encontrado");
    }
}
