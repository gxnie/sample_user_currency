package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.dto.ExchangeStatusDto;
import com.sparta.currency_user.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public ResponseEntity<ExchangeResponseDto> findExchange(@PathVariable Long id){
        return ResponseEntity.ok().body(exchangeService.findById(id));
    }

    // U -> 환전 요청 상태를 취소로 변경
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExchange(@PathVariable Long id,
                             @RequestBody ExchangeStatusDto exchangeStatusDto){

        // 환전 요청 상태를 업데이트하는 서비스
        exchangeService.updateExchangeById(id,exchangeStatusDto);

        // 환전 요청 상태가 정상적으로 업데이트되었을 때 메세지
        return ResponseEntity.ok().body("정상적으로 업데이트 되었습니다.");
    }

    // D -> 고객 삭제 + 모든 환전 요청도 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExchange(@PathVariable Long id){
        // 고객의 환전 요청을 삭제
        exchangeService.deleteExchangeById(id);

        // 삭제가 정상적으로 되었을 때 메세지
        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }



}
