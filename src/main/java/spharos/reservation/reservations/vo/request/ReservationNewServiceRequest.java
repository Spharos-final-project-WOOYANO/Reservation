package spharos.reservation.reservations.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationNewServiceRequest {

    //예약 상품 Id
    private Long reservationGoodsId;
    private Long serviceId;
    private Long workerId;
    private String userEmail;
    //서비스 날짜
    private LocalDate ReservationDate;
    //서비스 시작 & 종료시간
    private LocalTime serviceStart;
    private LocalTime serviceEnd;
    //결제 금액
    private Integer paymentAmount;
    //서비스 요청사항
    private String request;
    //서비스 받을 주소
    private String address;


}
