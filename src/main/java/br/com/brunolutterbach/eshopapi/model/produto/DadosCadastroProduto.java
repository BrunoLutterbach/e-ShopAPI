package br.com.brunolutterbach.eshopapi.model.produto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record DadosCadastroProduto(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal preco,
        @NotBlank
        String categoria,
        @NotBlank
        String marca,
        @Size(min = 1)
        List<String> images
) {
}
