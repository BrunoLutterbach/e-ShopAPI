package br.com.brunolutterbach.eshopapi.model.usuario;

import java.time.LocalDate;

public record DadosAtualizarUsuario(
    String nome,
    String email,
    String senha,
    String telefone,
    LocalDate dataNascimento
) {
}
