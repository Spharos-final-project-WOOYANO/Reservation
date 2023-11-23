package spharos.reservation.reservations.saga;


import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import spharos.payment.axon.command.SavePaymentCommand;
import spharos.reservation.reservations.axon.event.ChangeReservationStatusEvent;

@Saga
@Slf4j
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "reservation_num")
    public void createOrder(ChangeReservationStatusEvent event) {

        log.info("[StartSaga] saga");
        log.info("event: {}", event.getPaymentStatus());
        log.info("event: {}", event.getPaymentType());
        log.info("event: {}", event.getTotalAmount());
        log.info("event: {}", event.getApprovedAt());
        log.info("event: {}", event.getClientEmail());


        commandGateway.send(
                new SavePaymentCommand(UUID.randomUUID().toString(), event.getClientEmail(), event.getPaymentType()
                        , event.getTotalAmount(), event.getApprovedAt(), event.getPaymentStatus()));

    }

}
//
//        commandGateway.send(
//                new SavePaymentCommand(UUID.randomUUID().toString(), event.getClientEmail(), event.getPaymentType()
//                        , event.getTotalAmount(), event.getApprovedAt(), event.getPaymentStatus()),
//                                new CommandCallback<SavePaymentCommand, Object>() {
//        @Override
//        public void onResult(CommandMessage<? extends SavePaymentCommand> commandMessage,
//                CommandResultMessage<?> commandResultMessage) {
//            if (commandResultMessage.isExceptional()) {
//                // 보상 transaction
//                log.info("[보상Transaction]");
//                commandGateway.send(new CancelReservationCommand(event.getReservation_num(),
//                        ReservationState.PAYMENT_CANCEL));
//            }
//        }
//    });
/*
.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Failed to create order", throwable);
            }
            else{
                log.info("Order created successfully");
            }
        });}



   , new CommandCallback<SavePaymentCommand, Object>() {
            @Override
            public void onResult(CommandMessage<? extends SavePaymentCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                if(commandResultMessage.isExceptional()){
                    // 보상 transaction
                    log.info("[보상Transaction]");
                  //  commandGateway.send(new CancelOrderCommand(event.getOrderId()));
                }
            }
        });

    }*/

