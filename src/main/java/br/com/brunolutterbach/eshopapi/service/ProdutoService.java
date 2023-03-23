package br.com.brunolutterbach.eshopapi.service;

import br.com.brunolutterbach.eshopapi.model.produto.DadosAtualizarProduto;
import br.com.brunolutterbach.eshopapi.model.produto.DadosCadastroProduto;
import br.com.brunolutterbach.eshopapi.model.produto.DadosListagemProduto;
import br.com.brunolutterbach.eshopapi.model.produto.Produto;
import br.com.brunolutterbach.eshopapi.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProdutoService {

    final ProdutoRepository repository;

    public DadosListagemProduto cadastrar(DadosCadastroProduto dados) {
        var produto = new Produto(dados);
        repository.save(produto);
        return new DadosListagemProduto(produto);
    }

    public Page<DadosListagemProduto> listarProdutos(Pageable pageable) {
        return repository.findAll(pageable).map(DadosListagemProduto::new);
    }

    public DadosListagemProduto buscarPorId(Long id) {
        var produto = repository.getReferenceById(id);
        return new DadosListagemProduto(produto);
    }

    public DadosListagemProduto atualizarInformacoes(Long id, DadosAtualizarProduto dados) {
        var produto = repository.getReferenceById(id);
        produto.atualizar(dados);
        repository.save(produto);
        return new DadosListagemProduto(produto);
    }

    public void remover(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
        throw new IllegalArgumentException("Produto não encontrado");
    }
}
