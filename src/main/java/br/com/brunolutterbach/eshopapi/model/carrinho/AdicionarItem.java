package br.com.brunolutterbach.eshopapi.model.carrinho;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record AdicionarItem(@NotNull Long produtoId,
                            @NotNull
                            @Min(1)
                            int quantidade
) {
}
