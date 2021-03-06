package com.nhnacademy.marketgg.server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
public class CategoryRetrieveResponse {

    @NotBlank
    @Size(max = 6)
    private final String categoryCode;

    @NotBlank
    @Size(max = 20)
    private final String categorizationName;

    @NotBlank
    @Size(max = 20)
    private final String categoryName;

    @NotNull
    private final Integer sequence;

}
