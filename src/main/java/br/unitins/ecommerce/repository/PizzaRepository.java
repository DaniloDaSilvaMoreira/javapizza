package br.unitins.ecommerce.repository;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.produto.Marca;
import br.unitins.ecommerce.model.produto.pizza.Categoria;
import br.unitins.ecommerce.model.produto.pizza.Pizza;
import br.unitins.ecommerce.model.produto.pizza.TamanhoEmbalagem;
import br.unitins.ecommerce.model.produto.pizza.TamanhoPizza;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PizzaRepository implements PanacheRepository<Pizza>{
    
    public List<Pizza> findByNome(String nome) {

        if (nome == null)
            return null;

            return find("FROM Pizza WHERE UPPER(UNACCENT(nome)) LIKE UNACCENT(?1)",
             "%" + nome.toUpperCase() + "%").list();
    }

    public List<Pizza> findByCategoria (Categoria categoria) {

        if (categoria == null)
            return null;

        return find("FROM Pizza WHERE categoria = ?1", categoria).list();
    }

    public List<Pizza> findByTamanhoPizza (TamanhoPizza tamanhoPizza) {

        if (tamanhoPizza == null)
            return null;

        return find("FROM Pizza WHERE tamanhoPizza = ?1", tamanhoPizza).list();
    }

    public List<Pizza> findByTamanhoEmbalagem (TamanhoEmbalagem tamanhoEmbalagem) {

        if (tamanhoEmbalagem == null)
            return null;

        return find("FROM Pizza WHERE tamanhoEmbalagem = ?1", tamanhoEmbalagem).list();
    }

    public List<Pizza> findByMarca (Marca marca) {

        if (marca == null)
            return null;

        return find("FROM Pizza WHERE marca = ?1", marca).list();
    }

    public List<Pizza> filterByPrecoMaximo (Double preco) {

        if (preco == null)
            return null;

        return find("FROM Pizza WHERE preco < ?1", preco).list();
    }

    public List<Pizza> filterByPrecoMinimo (Double preco) {

        if (preco == null)
            return null;

        return find("FROM Pizza WHERE preco > ?1", preco).list();
    }

    public List<Pizza> filterByEntrePreco (Double precoMin, Double precoMax) {

        if (precoMin == null || precoMax == null)
            return null;

        return find("FROM Pizza WHERE preco > ?1 AND preco < ?2", precoMin, precoMax).list();
    }
}
