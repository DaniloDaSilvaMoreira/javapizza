package br.unitins.ecommerce.model.produto.pizza;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Categoria {

    DOCE(1, "Doce"),
    SALGADA(2, "Salgada");

    private int id;
    private String label;

    Categoria(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Categoria valueOf(Integer id) throws IllegalArgumentException {
        if (id == null) {
            return null;
        }

        for (Categoria categoria : Categoria.values()) {
            if (id.equals(categoria.getId())) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
    
}
