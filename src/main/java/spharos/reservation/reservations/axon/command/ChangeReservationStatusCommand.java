package spharos.reservation.reservations.axon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import spharos.reservation.reservations.domain.ReservationState;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChangeReservationStatusCommand {
    @TargetAggregateIdentifier
    private String reservation_num;

    private ReservationState status;
}
