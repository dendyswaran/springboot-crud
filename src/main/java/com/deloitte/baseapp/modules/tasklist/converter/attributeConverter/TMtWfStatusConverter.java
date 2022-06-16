package com.deloitte.baseapp.modules.tasklist.converter.attributeConverter;

import com.deloitte.baseapp.modules.tasklist.object.TMtWfStatusCd;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TMtWfStatusConverter implements AttributeConverter<TMtWfStatusCd, String> {
    @Override
    public String convertToDatabaseColumn(TMtWfStatusCd tMtWfStatusCd) {
        if (tMtWfStatusCd == null) {
            return null;
        }
        return tMtWfStatusCd.getTMtWfStatusCd();
    }

    @Override
    public TMtWfStatusCd convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return TMtWfStatusCd.of(s);
    }
}
