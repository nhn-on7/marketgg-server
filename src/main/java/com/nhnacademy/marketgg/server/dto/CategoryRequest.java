package com.nhnacademy.marketgg.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRequest {

    private Long superCategoryNo;

    private String name;

    private Integer sequence;

    private String code;

}