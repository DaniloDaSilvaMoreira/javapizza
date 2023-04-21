package br.unitins.ecommerce.converterjpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.unitins.ecommerce.model.produto.pizza.TamanhoPizza;

@Converter(autoApply = true)
public class TamanhoPizzaConverter implements AttributeConverter <TamanhoPizza, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TamanhoPizza tamanhoPizza) {
        return tamanhoPizza == null ? null : tamanhoPizza.getId();
    }

    @Override
    public TamanhoPizza convertToEntityAttribute(Integer id) {
        return TamanhoPizza.valueOf(id);
    }
}
