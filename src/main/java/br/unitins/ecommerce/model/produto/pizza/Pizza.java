package br.unitins.ecommerce.model.produto.pizza;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import br.unitins.ecommerce.model.produto.Produto;

@Entity
@PrimaryKeyJoinColumn(name = "id") // Herança de produto
public class Pizza extends Produto{

    @Column(nullable = false)
    private String bordaPizza;
    
    @ManyToMany
    @JoinTable(name = "sabores_pizza",
                joinColumns = @JoinColumn(name = "id_pizza"),
                inverseJoinColumns = @JoinColumn(name = "id_sabor"))
    private List<Sabor> sabor;

    private TamanhoEmbalagem tamanhoEmbalagem;
    private TamanhoPizza tamanhoPizza;
    private Categoria categoria;


    public String getBordaPizza() {
        return bordaPizza;
    }
    public void setBordaPizza(String bordaPizza) {
        this.bordaPizza = bordaPizza;
    }
    public TamanhoEmbalagem getTamanhoEmbalagem() {
        return tamanhoEmbalagem;
    }
    public void setTamanhoEmbalagem(TamanhoEmbalagem tamanhoEmbalagem) {
        this.tamanhoEmbalagem = tamanhoEmbalagem;
    }
    public TamanhoPizza getTamanhoPizza() {
        return tamanhoPizza;
    }
    public void setTamanhoPizza(TamanhoPizza tamanhoPizza) {
        this.tamanhoPizza = tamanhoPizza;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public List<Sabor> getSabor() {
        return sabor;
    }
    public void setSabor(List<Sabor> sabor) {
        this.sabor = sabor;
    } 
}
