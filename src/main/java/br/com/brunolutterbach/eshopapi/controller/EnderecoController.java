package br.com.brunolutterbach.eshopapi.controller;

import br.com.brunolutterbach.eshopapi.model.endereco.DadosCadastroEndereco;
import br.com.brunolutterbach.eshopapi.model.endereco.DadosDetalhamentoEndereco;
import br.com.brunolutterbach.eshopapi.model.endereco.Endereco;
import br.com.brunolutterbach.eshopapi.repository.EnderecoRepository;
import br.com.brunolutterbach.eshopapi.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("api/endereco")
@AllArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@SecurityRequirement(name = "bearer-key")
public class EnderecoController {

    final EnderecoRepository enderecoRepository;
    final UsuarioRepository usuarioRepository;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoEndereco> cadastrarEndereco(@RequestBody @Valid DadosCadastroEndereco dados, @PathVariable Long id,
                                               UriComponentsBuilder uriBuilder) {
        if (usuarioRepository.existsById(id)) {
            var endereco = new Endereco(dados, usuarioRepository.getReferenceById(id));
            enderecoRepository.save(endereco);
            var uri = uriBuilder.path("/endereco/{id}").buildAndExpand(endereco.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosDetalhamentoEndereco(endereco));
        }
        throw new IllegalArgumentException("Usuario de ID: " + id + " não encontrada");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoEndereco> atualizarEndereco(@PathVariable Long id, @RequestBody DadosCadastroEndereco dados) {
        var endereco = enderecoRepository.getReferenceById(id);
        endereco.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoEndereco(endereco));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> tornarEnderecoPrincipal(@PathVariable Long id) {
        if (enderecoRepository.existsByIdAndPrincipal(id, true)) {
            throw new IllegalArgumentException("Endereço já é o principal");
        }
        enderecoRepository.getReferenceById(id).getUsuario().getEnderecos().forEach(endereco -> endereco.setPrincipal(false));
        enderecoRepository.getReferenceById(id).setPrincipal(true);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarEndereco(@PathVariable Long id) {
        if (enderecoRepository.existsByIdAndPrincipal(id, true)) {
            throw new IllegalArgumentException("Id não encontrado ou endereço é o principal");
        }
        enderecoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

