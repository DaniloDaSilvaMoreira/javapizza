package br.unitins.ecommerce.converterjpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.unitins.ecommerce.model.produto.pizza.TamanhoEmbalagem;

@Converter(autoApply = true)
public class TamanhoEmbalagemConverter implements AttributeConverter <TamanhoEmbalagem, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TamanhoEmbalagem tamanhoEmbalagem) {
        return tamanhoEmbalagem == null ? null : tamanhoEmbalagem.getId();
    }

    @Override
    public TamanhoEmbalagem convertToEntityAttribute(Integer id) {
        return TamanhoEmbalagem.valueOf(id);
    }
}
