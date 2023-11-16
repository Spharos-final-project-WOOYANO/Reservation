package spharos.reservation.reservations.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.global.common.domain.BaseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ReservationGoods reservationGoods;
    @Column(nullable = false, length = 50, name = "user_email")
    private String userEmail;
    @Column(nullable = false, name = "service_id")
    private Long serviceId;
    @Column(name = "worker_id")
    private Long workerId;
    @Column(nullable = false, name = "reservation_date")
    private LocalDate reservationDate;
    @Column(nullable = false, name = "service_start")
    private LocalTime serviceStart;
    @Column(nullable = false, name = "service_end")
    private LocalTime serviceEnd;
    @Column(nullable = false)
    @Convert(converter = ReservationStateConverter.class)
    private ReservationState reservationState;
    @Column(nullable = false, name = "payment_amount")
    private Integer paymentAmount;
    @Column(length = 50, name = "cancel_desc")
    private String cancelDesc;
    @Column(length = 100, name = "request")
    private String request;
    @Column(nullable = false, length = 10, name = "reservation_num")
    private String reservationNum;
    @Column(nullable = false, length = 30, name = "address")
    private String address;
    @Builder
    public Reservation(ReservationGoods reservationGoods,
                       String userEmail,
                       Long serviceId,
                       Long workerId,
                       LocalDate reservationDate,
                       LocalTime serviceStart,
                       LocalTime serviceEnd,
                       ReservationState reservationState,
                       Integer paymentAmount,
                       String request,
                       String reservationNum,
                       String address) {
        this.reservationGoods = reservationGoods;
        this.userEmail = userEmail;
        this.serviceId = serviceId;
        this.workerId = workerId;
        this.reservationDate = reservationDate;
        this.serviceStart = serviceStart;
        this.serviceEnd = serviceEnd;
        this.reservationState = reservationState;
        this.paymentAmount = paymentAmount;
        this.request = request;
        this.reservationNum = reservationNum;
        this.address = address;
    }


    public static Reservation createReservation(ReservationGoods reservationGoods,
                                                String userEmail,
                                                Long serviceId,
                                                Long workerId,
                                                LocalDate reservationDate,
                                                LocalTime serviceStart,
                                                LocalTime serviceEnd,
                                                ReservationState reservationState,
                                                Integer paymentAmount,
                                                String request,
                                                String reservationNum,
                                                String address) {
        return new Reservation(reservationGoods, userEmail, serviceId, workerId, reservationDate, serviceStart, serviceEnd, reservationState, paymentAmount, request, reservationNum, address);
    }
}
