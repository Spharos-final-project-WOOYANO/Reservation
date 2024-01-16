package spharos.reservation.reservations.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.global.common.domain.BaseEntity;
import spharos.reservation.reservations.domain.enumPackage.ReservationStateConverter;
import spharos.reservation.reservations.domain.enumPackage.ReservationStatus;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationItem> reservationItems = new ArrayList<>();
    @Column(nullable = false, length = 50, name = "user_email")
    private String userEmail;
    @Column(nullable = false, name = "service_id")
    private Long serviceId;

    @JoinColumn(name = "worker_id")
    private Long workerId;
    @Column(nullable = false, name = "reservation_date")
    private LocalDate reservationDate;
    @Column(nullable = false, name = "service_start")
    private LocalTime serviceStart;
/*    @Column(nullable = false, name = "service_end")
    private LocalTime serviceEnd;*/
    @Column(nullable = false)
    @Convert(converter = ReservationStateConverter.class)
    private ReservationStatus reservationState;
    @Column(nullable = false, name = "payment_amount")
    private int totalPrice;
    @Column(length = 50, name = "cancel_desc")
    private String cancelDesc;
    @Column(length = 100, name = "request")
    private String request;

    @Column(nullable = false, length = 30, name = "address")
    private String address;

    @Column(nullable = false, length = 30, name = "order_id")
    private String orderId;

    private LocalDateTime approvedAt;


    public void approveStatus(ReservationStatus status) {
        this.reservationState = status;
    }

    public static Reservation createReservation(List<ReservationGoods> reservationGoods, String userEmail, Long serviceId,
                                                Long workerId, LocalDate reservationDate, LocalTime serviceStart,
                                                int totalPrice, String cancelDesc,
                                                String request, String address,String orderId,LocalDateTime approvedAt) {
        return Reservation.builder()
                .reservationGoods(reservationGoods)
                .userEmail(userEmail)
                .serviceId(serviceId)
                .workerId(workerId)
                .reservationDate(reservationDate)
                .serviceStart(serviceStart)
                .reservationState(ReservationStatus.WAIT)
                .totalPrice(totalPrice)
                .cancelDesc(cancelDesc)
                .request(request)
                .address(address)
                .orderId(orderId)
                .approvedAt(approvedAt)
                .build();
    }

    @Builder
    private Reservation(List<ReservationGoods> reservationGoods, String userEmail, Long serviceId, Long workerId,
                       LocalDate reservationDate, LocalTime serviceStart,
                       ReservationStatus reservationState, int totalPrice, String cancelDesc, String request,
                       String address,String orderId,LocalDateTime approvedAt) {
        this.reservationItems = reservationGoods.stream().map(item-> new ReservationItem(this,item)).collect(Collectors.toList());
        this.userEmail = userEmail;
        this.serviceId = serviceId;
        this.workerId = workerId;
        this.reservationDate = reservationDate;
        this.serviceStart = serviceStart;
        this.reservationState = reservationState;
        this.totalPrice = totalPrice;
        this.cancelDesc = cancelDesc;
        this.request = request;
        this.address = address;
        this.orderId = orderId;
        this.approvedAt = approvedAt;
    }


}
