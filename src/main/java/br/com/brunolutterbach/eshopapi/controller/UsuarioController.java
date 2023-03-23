package br.com.brunolutterbach.eshopapi.controller;

import br.com.brunolutterbach.eshopapi.model.usuario.DadosAtualizarUsuario;
import br.com.brunolutterbach.eshopapi.model.usuario.DadosCadastroUsuario;
import br.com.brunolutterbach.eshopapi.model.usuario.DadosListagemUsuario;
import br.com.brunolutterbach.eshopapi.model.usuario.DadosListagemUsuarioEndereco;
import br.com.brunolutterbach.eshopapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<DadosListagemUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder builder) {
        var usuarioResponse = usuarioService.cadastrar(dados);
        var uri = builder.path("/api/users/{id}").buildAndExpand(usuarioResponse.id()).toUri();
        return ResponseEntity.created(uri).body(usuarioResponse);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosListagemUsuario> atualizar(@PathVariable Long id, @RequestBody DadosAtualizarUsuario dados) {
        var usuario = usuarioService.atualizarInformacoes(id, dados);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemUsuario> getUsuario(@PathVariable Long id) {
        var usuarioResponse = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioResponse);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listarTodos(Pageable pageable) {
        var usuarioResponse = usuarioService.listarTodos(pageable);
        return ResponseEntity.ok(usuarioResponse);
    }

    @GetMapping("/{id}/enderecos")
    public ResponseEntity<DadosListagemUsuarioEndereco> listarEnderecosUsuario(@PathVariable Long id) {
        var usuarioResponse = usuarioService.listarEnderecos(id);
        return ResponseEntity.ok(usuarioResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
