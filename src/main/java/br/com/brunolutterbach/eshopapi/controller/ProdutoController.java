package br.com.brunolutterbach.eshopapi.controller;

import br.com.brunolutterbach.eshopapi.model.carrinho.AdicionarItem;
import br.com.brunolutterbach.eshopapi.model.carrinho.Carrinho;
import br.com.brunolutterbach.eshopapi.model.item.Item;
import br.com.brunolutterbach.eshopapi.model.produto.DadosAtualizarProduto;
import br.com.brunolutterbach.eshopapi.model.produto.DadosCadastroProduto;
import br.com.brunolutterbach.eshopapi.model.produto.DadosListagemProduto;
import br.com.brunolutterbach.eshopapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/produto")
@AllArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@SecurityRequirement(name = "bearer-key")
public class ProdutoController {

    final ProdutoService produtoService;

    @PostMapping()
    @Transactional
    public ResponseEntity<DadosListagemProduto> cadastrar(@RequestBody @Valid DadosCadastroProduto dados, UriComponentsBuilder builder) {
        var produtoResponse = produtoService.cadastrar(dados);
        var uri = builder.path("/api/products/{id}").buildAndExpand(produtoResponse.id()).toUri();
        return ResponseEntity.created(uri).body(produtoResponse);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosListagemProduto> atualizar(@PathVariable Long id, @RequestBody DadosAtualizarProduto dados) {
        var produto = produtoService.atualizarInformacoes(id, dados);
        return ResponseEntity.ok(produto);
    }

    @GetMapping()
    public ResponseEntity<Page<DadosListagemProduto>> listarProdutos(Pageable pageable) {
        Page<DadosListagemProduto> dadosListagemProdutos = produtoService.listarProdutos(pageable);
        return ResponseEntity.ok(dadosListagemProdutos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemProduto> obterProduto(@PathVariable Long id) {
        var produtoResponse = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produtoResponse);
    }

    @PostMapping("/carrinho")
    public String adicionarAoCarrinho(@RequestBody AdicionarItem dto, HttpSession session) {
        var produto = produtoService.buscarPorId(dto.produtoId());
        Item item = new Item(produto, dto.quantidade());

        // recupera o carrinho da sessão ou cria um novo se não existir
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }

        // atualiza o carrinho na sessão
        carrinho.adicionarItem(item);

        // Redireciona para a página do carrinho
        return "redirect:/carrinho";
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
        produtoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}