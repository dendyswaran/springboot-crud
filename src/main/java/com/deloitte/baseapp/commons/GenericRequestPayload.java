package com.deloitte.baseapp.commons;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GenericRequestPayload {
    @NotBlank
    private String name;

    @NotBlank
    private String code;

}
