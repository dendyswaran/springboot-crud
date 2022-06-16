package com.deloitte.baseapp.modules.tasklist.object;

import java.util.Objects;
import java.util.stream.Stream;

public enum TMtWfStatusCd {
    NEW("0"),
    PENDING_DECOM("1"),
    PENDING_SCRAP_OEM("2"),
    PARTIAL_COMPLETE("3"),
    COMPLETED("4");

    private final String tMtWfStatusCd;

    TMtWfStatusCd(String tMtWfStatusCd) {
        this.tMtWfStatusCd = tMtWfStatusCd;
    }

    public String getTMtWfStatusCd() {
        return tMtWfStatusCd;
    }

    public static TMtWfStatusCd of(String tMtWfStatusCd) {
        return Stream.of(TMtWfStatusCd.values())
                .filter(e -> Objects.equals(e.getTMtWfStatusCd(), tMtWfStatusCd))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
