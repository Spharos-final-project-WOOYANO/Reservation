package spharos.reservation.reservations.axon.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.reservations.axon.command.PaymentStatus;
import spharos.reservation.reservations.axon.command.PaymentType;
import spharos.reservation.reservations.domain.ReservationState;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChangeReservationStatusEvent {
    private String reservation_num;
    private ReservationState status;

    private String clientEmail; //사업자 이메일
    private String paymentType; //"0"
    private int totalAmount; //결제 금액
    private LocalDateTime approvedAt; //결제 완료,취소가 일어난 날짜와 시간 정보
    private String paymentStatus; //"0"

    private String paymentKey; //결제 고유 번호

}
