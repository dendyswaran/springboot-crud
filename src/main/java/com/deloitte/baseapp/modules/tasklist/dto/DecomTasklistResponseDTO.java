package com.deloitte.baseapp.modules.tasklist.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DecomTasklistResponseDTO {

    private Long id;
    private String cd;
    private int eqpCount;
    private LocalDateTime dtDecom;

}
