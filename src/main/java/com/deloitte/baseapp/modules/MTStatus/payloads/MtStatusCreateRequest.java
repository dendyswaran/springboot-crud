package com.deloitte.baseapp.modules.MTStatus.payloads;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MtStatusCreateRequest {
    @NotBlank
    private String dscp;

    @NotBlank
    private String code;
}
