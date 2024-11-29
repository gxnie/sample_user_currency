package com.sparta.currency_user.dto;


import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExchangeRequestDto {

    private Long userId;         // 요청한 유저 ID

    private Long currencyId;     // 환전 대상 통화 ID

    private BigDecimal amountInKrw;  // 환전 전 금액 (원 기준)

    public ExchangeRequestDto(Long userId, Long currencyId, BigDecimal amountInKrw) {
        this.userId = userId;
        this.currencyId = currencyId;
        this.amountInKrw = amountInKrw;
    }


}

