package spharos.reservation.reservations.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import spharos.reservation.reservations.axon.command.CreateReservationCommand;
import spharos.reservation.reservations.axon.event.ReservationCreateEvent;
import spharos.reservation.reservations.domain.ReservationState;

@Aggregate
@NoArgsConstructor
public class ReservationAggregate {

    @AggregateIdentifier
    private String id;
    private ReservationState reservationState;

    @CommandHandler
    public ReservationAggregate(CreateReservationCommand command) {
        ReservationCreateEvent reservationCreateEvent = new ReservationCreateEvent(command.getReservationId(),
                command.getReservationGoodsId(), command.getServiceId(), command.getWorkerId(), command.getUserEmail(),
                command.getReservationDate(), command.getServiceStart(), command.getServiceEnd(), command.getPaymentAmount(),
                 command.getRequest(),command.getReservationNum(),command.getAddress());
        apply(reservationCreateEvent);
    }

  /*  @CommandHandler
    public void cancelOrder(CancelOrderCommand command) {
        apply(new OrderCanceledEvent(command.getOrderId()));
    }*/

    @EventSourcingHandler
    public void createOrder(ReservationCreateEvent event) {
        this.id = event.getReservationId();
        this.reservationState = ReservationState.PAVEMENT_WAITING;
    }

  /*  @EventSourcingHandler
    public void cancelOrder(OrderCanceledEvent event) {
        this.orderStatus = OrderStatus.CANCELED;
    }*/
}
