package com.deloitte.baseapp.modules.tasklist.converter.attributeConverter;

import com.deloitte.baseapp.modules.tasklist.object.TMtMakeCd;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TMtMakeCdConverter implements AttributeConverter<TMtMakeCd, String> {
    @Override
    public String convertToDatabaseColumn(TMtMakeCd tMtMakeCd) {

        if (tMtMakeCd == null) {
            return null;
        }
        return tMtMakeCd.getTMtMakeCd();
    }

    @Override
    public TMtMakeCd convertToEntityAttribute(String s) {

        if (s == null) {
            return null;
        }
        return TMtMakeCd.of(s);
    }

}
