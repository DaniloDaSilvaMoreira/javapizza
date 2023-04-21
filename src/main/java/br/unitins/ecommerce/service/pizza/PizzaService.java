package br.unitins.ecommerce.service.pizza;

import java.util.List;

import br.unitins.ecommerce.dto.pizza.PizzaDTO;
import br.unitins.ecommerce.dto.pizza.PizzaResponseDTO;

public interface PizzaService {
    
    List<PizzaResponseDTO> getAll();
    
    PizzaResponseDTO getById(Long id);

    PizzaResponseDTO insert(PizzaDTO pizzaDto);

    PizzaResponseDTO update(Long id, PizzaDTO pizzaDto);

    void delete(Long id);

    //

    Long count();

    List<PizzaResponseDTO> getByNome(String nome);

    List<PizzaResponseDTO> getByCategoria(Integer id);

    List<PizzaResponseDTO> getByTamanhoEmbalagem(Integer id);

    List<PizzaResponseDTO> getByTamanhoPizza(Integer id);

    List<PizzaResponseDTO> getByMarca(String nome);

    //

    List<PizzaResponseDTO> filterByPrecoMin(Double preco);

    List<PizzaResponseDTO> filterByPrecoMax(Double preco);

    List<PizzaResponseDTO> filterByEntrePreco(Double precoMin, Double precoMax);
    
}
