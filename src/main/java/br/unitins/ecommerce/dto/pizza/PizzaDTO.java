package br.unitins.ecommerce.dto.pizza;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record PizzaDTO (
    
    @NotBlank(message = "Campo nome não pode estar vazio")
    String nome,

    @NotBlank(message = "Campo bordaPizza não pode estar vazio")
    String bordaPizza,

    @NotNull
    @Min(1)
    Long idMarca,

    @NotNull
    @Min(1)
    Double preco,

    @NotNull
    @Min(0)
    Integer estoque,

    @NotNull
    @Min(1)
    @Max(5)
    Integer tamanhoPizza,

    @NotNull
    @Min(1)
    @Max(5)
    Integer tamanhoEmbalagem,

    @NotNull
    @Min(1)
    @Max(2)
    Integer categoria,

    @NotNull
    List<Long> idSabores

)
    {
}
