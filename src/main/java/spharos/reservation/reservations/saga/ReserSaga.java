package spharos.reservation.reservations.saga;


import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import spharos.payment.axon.command.SavePaymentCommand;
import spharos.client.axon.command.SendReservationNotificationCommand;
import spharos.reservation.reservations.axon.event.PaymentSaveEvent;
import spharos.reservation.reservations.axon.event.ChangeReservationStatusEvent;


@Slf4j
@Saga
public class ReserSaga {

    @Autowired
    private transient CommandGateway commandGateway;


    @StartSaga
    @SagaEventHandler(associationProperty = "reservation_num")
    public void on(ChangeReservationStatusEvent event) {

        log.info("[StartSaga] saga");

        log.info("event: {}", event.getPaymentStatus());
        log.info("event: {}", event.getPaymentType());
        log.info("event: {}", event.getTotalAmount());
        log.info("event: {}", event.getApprovedAt());
        log.info("event: {}", event.getClientEmail());
        log.info("event: {}", event.getReservation_num());


        commandGateway.send(
                new SavePaymentCommand(UUID.randomUUID().toString(), event.getClientEmail(),event.getPaymentType()
                        , event.getTotalAmount(), event.getApprovedAt(),event.getPaymentStatus())) .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.error(" [보상Transaction]", throwable);
                    }
                    else{
                        log.info("Order created successfully");
                    }});
    }



/*
    @SagaEventHandler(associationProperty = "reservation_num")
    public void handleSavePaymentEvent(PaymentSaveEvent event) {
        log.info("[SagaEventHandler] handleSavePaymentEvent");
        commandGateway.send(new SendReservationNotificationCommand(event.getReservation_num()) );
    }
*/





/*    @SagaEventHandler(associationProperty = "reservation_num")
    public void endSaga(CancelReservationStatusEvent event) {
        log.info("[EndSaga] saga");
        SagaLifecycle.end();
    }*/





/*
    @SagaEventHandler(associationProperty = "reservation_num")
    public void end(ChangeReservationStatusEvent event) {
        log.info("[EndSaga] saga");

    }
*/





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
//.whenComplete((result, throwable) -> {
//            if (throwable != null) {
//                log.error("Failed to create order", throwable);
//            }
//            else{
//                log.info("Order created successfully");
//            }
//        });}
//
//
//
//   , new CommandCallback<SavePaymentCommand, Object>() {
//            @Override
//            public void onResult(CommandMessage<? extends SavePaymentCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
//                if(commandResultMessage.isExceptional()){
//                    // 보상 transaction
//                    log.info("[보상Transaction]");
//                  //  commandGateway.send(new CancelOrderCommand(event.getOrderId()));
//                }
//            }
//        });
//
//    }
//    @EndSaga
//    @SagaEventHandler(associationProperty = "reservation_num")
//    public void end(ChangeReservationStatusEvent event) {
//        log.info("[EndSaga] saga");
//
//    }


