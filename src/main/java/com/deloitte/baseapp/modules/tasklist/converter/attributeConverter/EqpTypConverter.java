package com.deloitte.baseapp.modules.tasklist.converter.attributeConverter;

import com.deloitte.baseapp.modules.tasklist.object.EqpType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EqpTypConverter implements AttributeConverter<EqpType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EqpType eqpType) {
        if (eqpType == null) {
            return null;
        }
        return eqpType.getEqpType();
    }

    @Override
    public EqpType convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }
        return EqpType.of(code);
    }

}
