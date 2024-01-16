package spharos.reservation.reservations.axon.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.reservations.domain.enumPackage.ReservationStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationCreateEvent {

    private String orderId;
    private int amount;
    private Long serviceId;
    private String userEmail;
    private LocalDate reservationDate;
    private String request;
    private String address;
    private LocalTime serviceStart;
    private Long workerId;
    private List<Long> reservationGoodsId;
    private LocalDateTime approvedAt;

}
