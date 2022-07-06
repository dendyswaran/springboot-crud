package com.deloitte.baseapp.modules.tasklist.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IohTasklistResponseDTO {

    private Long id;
    private String cd;
    private String nm;
    private int eqpCount;
}
