package com.deloitte.baseapp.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class GenericResponse {
    private UUID id;
    private String name;
    private String code;
}
