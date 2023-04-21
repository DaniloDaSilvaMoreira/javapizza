package br.unitins.ecommerce.model.produto.pizza;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.unitins.ecommerce.model.DefaultEntity;

@Entity
public class Sabor extends DefaultEntity{

    @Column(nullable = false)
    private String nome;


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
