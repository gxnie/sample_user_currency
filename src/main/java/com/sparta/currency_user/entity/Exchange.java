package com.sparta.currency_user.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@NoArgsConstructor
public class Exchange extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 환전 고유 식별자

    private BigDecimal amountInKrw; // 환전 전 금액

    private BigDecimal amountAfterExchange; // 환전 후 금액

    private String status; // 상태(normal 또는 cancelled

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id", insertable = false, updatable = false)
    private Currency currency;
    // currency_id를 연결해주기 위해 Currency 객체를 가져옴 -> 환율(exchange_rate)도 포함되어있음

    public Exchange(Long id, BigDecimal amountInKrw, BigDecimal amountAfterExchange, String status, User user, Currency currency) {
        this.id = id;
        this.amountInKrw = amountInKrw;
        this.amountAfterExchange = amountAfterExchange;
        this.status = status;
        this.user = user;
        this.currency = currency;
    }

    // 환전 계산하기
    public Exchange(BigDecimal amountInKrw, BigDecimal exchangeRate, String status) {
        // 환전 전 금액, 환율을 입력받아서, 계산한 값을 환전 후 금액에 넣는다.

        // 환전 전 금액
        this.amountInKrw = amountInKrw;

        // 환전 후 금액(amount_after_exchange) = 환전 전 금액(amount_in_krw) / 환율(exchange_rate)
        // amountInKrw : 환전 전 금액, divide : 나누기, exchangeRate : 환율 ,2 : 소수점 아래 수, RoundingMode.Half_UP : 가장 가까운 값으로 반올림
        this.amountAfterExchange = amountInKrw.divide(exchangeRate,2 , RoundingMode.HALF_UP);

        // normal or cancelled 두개 있음
        this.status = status;
    }

    // setter 사용 ?  명확한 메서드 사용?
    public void setUser(User user){
        this.user = user;
    }

    public void setCurrency(Currency currency){
        this.currency = currency;
    }
}
