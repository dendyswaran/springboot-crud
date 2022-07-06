package com.deloitte.baseapp.modules.tasklist.object;

import java.util.Objects;
import java.util.stream.Stream;

public enum TMtMakeCd {
    COM_POWER("0"),
    ERICSSON("1"),
    NOKIA("2"),
    TELEVES("3"),
    OTHERS("4");

    private final String tMtMakeCd;

    TMtMakeCd(String tMtMakeCd) {
        this.tMtMakeCd = tMtMakeCd;
    }

    public String getTMtMakeCd() {
        return tMtMakeCd;
    }

    public static TMtMakeCd of(String tMtMakeCd) {
        return Stream.of(TMtMakeCd.values())
                .filter(e -> Objects.equals(e.getTMtMakeCd(), tMtMakeCd))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
