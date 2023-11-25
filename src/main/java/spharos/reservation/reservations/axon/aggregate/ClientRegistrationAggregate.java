package spharos.reservation.reservations.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import spharos.reservation.reservations.axon.command.ClientRegistrationCommand;
import spharos.reservation.reservations.axon.event.ClientRegistrationEvent;


@Aggregate()
@Data
@Slf4j
public class ClientRegistrationAggregate {

    @AggregateIdentifier
    private String id;

    private Long clientId;
    private String clientName;

    @CommandHandler
    public ClientRegistrationAggregate(ClientRegistrationCommand command) {
        try {
            System.out.println("ClientRegistrationAggregate constructor");
            apply(new ClientRegistrationEvent(command.getClientId()));
        } catch (Exception e) {
            log.error("Error handling command", e);
            throw new RuntimeException("Error handling command", e);
        }
    }


    @EventSourcingHandler
    public void on(ClientRegistrationEvent event) {
        try {
            System.out.println("ClientRegistrationAggregate event handler");
            id = UUID.randomUUID().toString();
            log.info("ClientRegistrationEvent: {}", event.getClientId());
            clientId = NumberUtils.toLong(event.getClientId());

            // clientId = 1l;
            log.info("ClientRegistrationEvent: {}", event.getClientId());
        } catch (Exception e) {
            log.error("Error handling event", e);
            throw new RuntimeException("Error handling event", e);
        }
    }

    public ClientRegistrationAggregate() {
    }
}
