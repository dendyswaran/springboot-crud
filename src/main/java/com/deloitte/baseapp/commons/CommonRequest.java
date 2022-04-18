package com.deloitte.baseapp.commons;

import lombok.Data;

import java.util.List;

public class CommonRequest {

    @Data
    public static class BulkDelete {
        private List<Long> ids;
    }

}
