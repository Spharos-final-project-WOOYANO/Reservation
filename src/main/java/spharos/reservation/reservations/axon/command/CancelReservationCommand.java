package spharos.reservation.reservations.axon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import spharos.reservation.reservations.domain.ReservationState;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CancelReservationCommand {
    @TargetAggregateIdentifier
    private String reservationNum; //예약 번호
    private String paymentKey; //결제 고유번호

    }
