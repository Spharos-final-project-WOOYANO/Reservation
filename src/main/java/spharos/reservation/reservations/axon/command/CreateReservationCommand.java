package spharos.reservation.reservations.axon.command;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class CreateReservationCommand {
    @TargetAggregateIdentifier
    private String reservationId;
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
   // private Integer status; //예약 상태


    public CreateReservationCommand(String reservationId, Long reservationGoodsId, Long serviceId, Long workerId,
                                    String userEmail, LocalDate reservationDate, LocalTime serviceStart,
                                    LocalTime serviceEnd, Integer paymentAmount, String request, String reservationNum,
                                    String address) {
        this.reservationId = reservationId;
        this.reservationGoodsId = reservationGoodsId;
        this.serviceId = serviceId;
        this.workerId = workerId;
        this.userEmail = userEmail;
        this.reservationDate = reservationDate;
        this.serviceStart = serviceStart;
        this.serviceEnd = serviceEnd;
        this.paymentAmount = paymentAmount;
        this.request = request;
        this.reservationNum = reservationNum;
        this.address = address;
    }
}
