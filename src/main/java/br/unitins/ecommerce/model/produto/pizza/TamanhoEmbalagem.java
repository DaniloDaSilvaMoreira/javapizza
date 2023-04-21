package br.unitins.ecommerce.model.produto.pizza;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TamanhoEmbalagem {

    PEQUENA_25x25(1, "Pequena"),
    MEDIA_30X30(2, "Media"),
    GRANDE_35X35(3, "Grande"),
    FAMILIA_42X42(4, "Familia"),
    QUADRADA_36X36(5, "Quadrada");

    private int id;
    private String label;

    TamanhoEmbalagem(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TamanhoEmbalagem valueOf(Integer id) throws IllegalArgumentException {
        if (id == null) {
            return null;
        }

        for (TamanhoEmbalagem tamanhoEmbalagem : TamanhoEmbalagem.values()) {
            if (id.equals(tamanhoEmbalagem.getId())) {
                return tamanhoEmbalagem;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
    
}
