package spharos.client.axon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SendReservationNotificationCommand {
    @TargetAggregateIdentifier
    private String reservationNum; //예약 번호
}
