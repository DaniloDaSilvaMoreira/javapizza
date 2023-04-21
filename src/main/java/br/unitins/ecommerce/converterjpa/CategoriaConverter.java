package br.unitins.ecommerce.converterjpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.unitins.ecommerce.model.produto.pizza.Categoria;

@Converter(autoApply = true)
public class CategoriaConverter implements AttributeConverter <Categoria, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Categoria categoria) {
        return categoria == null ? null : categoria.getId();
    }

    @Override
    public Categoria convertToEntityAttribute(Integer id) {
        return Categoria.valueOf(id);
    }
}
