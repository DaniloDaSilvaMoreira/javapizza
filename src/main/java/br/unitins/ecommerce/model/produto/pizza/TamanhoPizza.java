package br.unitins.ecommerce.model.produto.pizza;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TamanhoPizza {

    PEQUENA(1, "Pequena"),
    MEDIA(2, "Media"),
    GRANDE(3, "Grande"),
    FAMILIA(4, "Familia"),
    QUADRADA(5, "Quadrada");

    private int id;
    private String label;

    TamanhoPizza(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TamanhoPizza valueOf(Integer id) throws IllegalArgumentException {
        if (id == null) {
            return null;
        }

        for (TamanhoPizza tamanhoPizza : TamanhoPizza.values()) {
            if (id.equals(tamanhoPizza.getId())) {
                return tamanhoPizza;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
    
}
