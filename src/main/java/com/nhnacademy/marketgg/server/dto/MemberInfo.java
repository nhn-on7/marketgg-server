package com.nhnacademy.marketgg.server.dto;

import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberInfo {

    private final Long id;
    private final Cart cart;
    private final MemberGrade memberGrade;
    private final Character gender;
    private final LocalDate birthDate;
    private final LocalDateTime ggpassUpdatedAt;

}
