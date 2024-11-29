package com.sparta.currency_user.dto;


import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.entity.User;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
public class ExchangeResponseDto {


    private Long id;                 // 환전 요청 ID

    private Long userId;            // 고객 ID

    private Long currencyId;        // 통화 ID

    private BigDecimal amountInKrw;      // 환전 전 금액(원)

    private BigDecimal amountAfterExchange; // 환전 후 금액

    private String status;           // 상태

    private LocalDateTime createdAt; // 생성일

    private LocalDateTime modifiedAt; // 수정일


    public ExchangeResponseDto(Long id, Long userId, Long currencyId, BigDecimal amountInKrw, BigDecimal amountAfterExchange,
                               String status,LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.currencyId = currencyId;
        this.amountInKrw = amountInKrw;
        this.amountAfterExchange = amountAfterExchange;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ExchangeResponseDto(Exchange exchange){
        this.id = exchange.getId();
        this.userId = exchange.getUser().getId();
        this.currencyId = exchange.getCurrency().getId();
        this.amountInKrw = exchange.getAmountInKrw();
        this.amountAfterExchange = exchange.getAmountAfterExchange();
        this.status = exchange.getStatus();
        this.createdAt = exchange.getCreatedAt();
        this.modifiedAt = exchange.getModifiedAt();
    }

    public ExchangeResponseDto(){

    }

}
