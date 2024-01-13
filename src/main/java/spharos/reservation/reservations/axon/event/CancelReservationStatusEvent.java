package spharos.reservation.reservations.axon.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.reservations.domain.enumPackage.ReservationStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CancelReservationStatusEvent {
    private String reservation_num;
    private ReservationStatus status;
    private String paymentKey;
}
