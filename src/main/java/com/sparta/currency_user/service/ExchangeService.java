package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.dto.ExchangeStatusDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.repository.ExchangeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class ExchangeService {


    final ExchangeRepository exchangeRepository;

    final UserService userService;

    final CurrencyService currencyService;


    // C -> 환전 요청 수행
    public ExchangeResponseDto save(Long userId, Long currencyId, BigDecimal amountInKrw){
        // 환전하기 전 알아야할 정보 : user_id(고객), currency_id(통화)
        // 1. 고객 id 가져오기(누가 요청했는지 알아야함)
        User findUser = userService.findUserById(userId);

        // 2. 통화 id 가져오기(어떤 통화를 사용하는지 알아야함)
        Currency findCurrency = currencyService.findCurrencyById(currencyId);

        // 3. 환전 요청(환전한 것 가져오기)
        // 환전 전 금액, 환율을 입력하면, 환전 계산 -> exchange에 저장
        // status : normal , cancelled 두 개가 있는데, 환전하면 무조건 normal
        Exchange exchange = new Exchange(amountInKrw, findCurrency.getExchangeRate(),"normal");

        // 4. exchange 객체의 user 속성에 특정 사용자(user_id)를 설정
        exchange.setUser(findUser);

        // 5. exchange 객체의 currency 속성에 특정 통화(currency_id)를 설정
        exchange.setCurrency(findCurrency);

        // 4,5번 => 환전한 사용자를 설정하고, 환전한 통화를 설정
        // DB에 환전한 값 저장
        exchangeRepository.save(exchange);

        // 6. ResponseDto -> 필요한 데이터만 정리한 전달용 객체(다 꺼내기에는, 중요한 정보가 있을 수 있음)
        return new ExchangeResponseDto(
                exchange.getId(),                     // 환전 요청 id
                exchange.getUser().getId(),           // 사용자 id(환전 요청한 사용자의 고유 ID)
                exchange.getCurrency().getId(),       // 통화 id(통화 객체 고유 ID 반환)
                exchange.getAmountInKrw(),            // 환전 전 금액 (원 기준)
                exchange.getAmountAfterExchange(),    // 환전 후 금액 (대상 통화 기준)
                exchange.getStatus(),                 // 환전 상태 (normal or cancelled)
                exchange.getCreatedAt(),              // 생성일자
                exchange.getModifiedAt()              // 수정일자
        );

    }

    // R -> 고객 고유 식별자(User_id)로 특정 고객 환전 요청 조회
    public ExchangeResponseDto findById(Long id){
        return new ExchangeResponseDto(findExchangeById(id));
    }

    public Exchange findExchangeById(Long id){
        // 특정 id에 해당하는 Exchange를 반환하는 코드
        return exchangeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 정보가 없습니다."));
    }


    // U -> 환전 요청 상태를 취소로 변경
    public User updateExchangeById(Long id, ExchangeStatusDto exchangeStatusDto){
        // 환전 요청 찾기
        Exchange findExchange = this.findExchangeById(id);

        // 상태 업데이트
        findExchange.updateByDTO(exchangeStatusDto);

        // 변경된 환전 요청(상태) 저장
        exchangeRepository.save(findExchange);

        // 해당 요청과 관련된 사용자 반환
        // Exchange에서 User를 가져온 후 반환
        return findExchange.getUser();
    }


    // D -> 고객 삭제(user Delete있음) + 모든 환전 요청도 삭제
    @Transactional
    public void deleteExchangeById(Long id) {
        // 1. id로 환전 요청을 조회
        // 만약 id에 해당하는 환전 요청이 없으면 예외가 발생 -> 트랜잭션 처음으로 돌아간다..?
        // 환전 요청을 찾기 위해 호출
        this.findExchangeById(id);

        // 2. 환전 요청을 삭제
        // exchangeRepository.deleteById(id) : 해당 id 환전 요청을 DB에서 삭제
        exchangeRepository.deleteById(id);
    }
}
