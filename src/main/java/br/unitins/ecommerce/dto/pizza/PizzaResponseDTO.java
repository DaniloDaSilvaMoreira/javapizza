package br.unitins.ecommerce.dto.pizza;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.ecommerce.model.produto.pizza.Categoria;
import br.unitins.ecommerce.model.produto.pizza.Pizza;
import br.unitins.ecommerce.model.produto.pizza.Sabor;
import br.unitins.ecommerce.model.produto.pizza.TamanhoEmbalagem;
import br.unitins.ecommerce.model.produto.pizza.TamanhoPizza;

public record PizzaResponseDTO (

    Long id,
    String nome,
    Double preco,
    String estoque,
    String nomeMarca,
    String bordaPizza,
    List<String> sabores,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    TamanhoEmbalagem tamanhoEmbalagem,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    TamanhoPizza tamanhoPizza,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Categoria categoria

)

{   
    public PizzaResponseDTO(Pizza pizza) {

        this(pizza.getId(),
            pizza.getNome(),
            pizza.getPreco(),
            pizza.getEstoque() > 0? "Disponível" : "Estoque esgotado",
            pizza.getMarca().getNome(),
            pizza.getBordaPizza(),
            viewSabores(pizza.getSabor()),
            pizza.getTamanhoEmbalagem(),
            pizza.getTamanhoPizza(),
            pizza.getCategoria()
            );
    } 

    private static List<String> viewSabores (List<Sabor> lista) {

        List<String> nomeSabores = new ArrayList<>();

        for (Sabor sabor : lista) {
            
            nomeSabores.add(sabor.getNome());
        }

        return nomeSabores;
    }
}
