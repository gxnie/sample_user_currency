package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    final ExchangeService exchangeService;

    ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    // C -> 환전 요청 수행
    @PostMapping
    public ResponseEntity<ExchangeResponseDto> createExchange(@RequestBody ExchangeRequestDto requestDto){
        return ResponseEntity.ok().body(exchangeService.save(requestDto.getUserId(), requestDto.getCurrencyId(), requestDto.getAmountInKrw()));
        // user id(사용자 id), currency id(통화 id), AmountInKrw(환전 전 금액)
    }


    // R -> 고객 고유 식별자(User_id)로 특정 고객 환전 요청 조회


    // U -> 환전 요청 상태를 취소로 변경


    // D -> 고객 삭제 + 모든 환전 요청도 삭제




}
