package br.unitins.ecommerce.repository;

import javax.enterprise.context.ApplicationScoped;

import br.unitins.ecommerce.model.produto.pizza.Sabor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SaborRepository implements PanacheRepository<Sabor> {
    
}
