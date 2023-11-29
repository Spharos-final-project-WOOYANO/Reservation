package spharos.reservation.reservations.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import spharos.reservation.reservations.application.ReservationService;

@Component
@Slf4j
public class ReservationDecisionEventConsumer {

    @Autowired
    private ReservationService reservationService;


    @KafkaListener(topics = "reservation-accept-events",groupId = "reservation")
    public void onMessage(String message) throws JsonProcessingException {
        log.info("Consumer Record : {}", message);
        reservationService.processReservationDecisionEvent(message);
    }

}
