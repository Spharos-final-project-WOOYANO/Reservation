package spharos.reservation.reservations.saga;


import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import spharos.payment.axon.command.SavePaymentCommand;
import spharos.reservation.reservations.axon.command.CancelReservationCommand;
import spharos.reservation.reservations.axon.command.PaymentStatus;
import spharos.reservation.reservations.axon.command.PaymentStatusConverter;
import spharos.reservation.reservations.axon.command.PaymentType;
import spharos.reservation.reservations.axon.command.PaymentTypeConverter;
import spharos.reservation.reservations.axon.event.CancelReservationStatusEvent;
import spharos.reservation.reservations.axon.event.ChangeReservationStatusEvent;

@Slf4j
@Saga
public class ReservationSaga {

    @Autowired
    private transient CommandGateway commandGateway;



    @StartSaga
    @SagaEventHandler(associationProperty = "reservation_num")
    public void createReservation(ChangeReservationStatusEvent event) {

        log.info("[StartSaga] saga");
        log.info("event: {}", event.getPaymentStatus());
        log.info("event: {}", event.getPaymentType());
        log.info("event: {}", event.getTotalAmount());
        log.info("event: {}", event.getApprovedAt());
        log.info("event: {}", event.getClientEmail());


        commandGateway.send(
                new SavePaymentCommand(UUID.randomUUID().toString(), event.getClientEmail(),event.getPaymentType()
                        , event.getTotalAmount(), event.getApprovedAt(),event.getPaymentStatus()), new CommandCallback<SavePaymentCommand, Object>() {
                    @Override
                    public void onResult(CommandMessage<? extends SavePaymentCommand> commandMessage, CommandResultMessage<?> commandResultMessage) {
                        if(commandResultMessage.isExceptional()){
                            // 보상 transaction
                            log.info("[보상Transaction] cancel order");
                            commandGateway.send(new CancelReservationCommand(event.getReservation_num()));
                        }
                    }
                });

    }

    @SagaEventHandler(associationProperty = "reservation_num")
    public void endSaga(CancelReservationStatusEvent event) {
        log.info("[EndSaga] saga");
        SagaLifecycle.end();
    }


    @EndSaga
    @SagaEventHandler(associationProperty = "reservation_num")
    public void end(ChangeReservationStatusEvent event) {
        log.info("[EndSaga] saga");

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


