package spharos.reservation.reservations.axon.event;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.reservations.domain.ReservationState;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationCreateEvent {

    private Long reservationGoodsId; //예약 상품id
    private Long serviceId; //서비스id
    private Long workerId; //작업자id
    private String userEmail; //유저 이메일
    private LocalDate reservationDate; //예약 날짜
    private LocalTime serviceStart; // 서비스 시작 시간
    private LocalTime serviceEnd;  // 서비스 종료 시간
    private Integer paymentAmount; //결제 금액
    private String request; //요청사항
    private String reservationNum; //예약 번호
    private String address; //주소
    private ReservationState status; //예약 상태
}
