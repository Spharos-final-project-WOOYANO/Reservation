package spharos.reservation.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK,true, 200, "요청에 성공하였습니다."),


    /**
     * 에러 코드
     **/
    CANNOT_FIND_RESERVATION(HttpStatus.OK,true, 3010, "예약번호로 조회되는 예약이 존재하지 않습니다."),
    CANNOT_FIND_RESERVATION_GOODS(HttpStatus.OK, true, 3011, "예약 상품 번호로 조회되는 예약상품이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final boolean success;
    private final int code;
    private final String message;

}
