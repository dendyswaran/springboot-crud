package com.deloitte.baseapp.modules.tasklist.object;

import java.util.Objects;
import java.util.stream.Stream;

// TODO: discuss to keep as integer or change to string.
public enum EqpType {
    SCRAP(0),
    REUSE(1);

    private final Integer eqpType;

    EqpType(Integer eqpType) {
        this.eqpType = eqpType;
    }

    public Integer getEqpType() {
        return eqpType;
    }

    public static EqpType of(Integer eqpType) {
        return Stream.of(EqpType.values())
                .filter(e -> Objects.equals(e.getEqpType(), eqpType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
