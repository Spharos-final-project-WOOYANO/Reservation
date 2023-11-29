package spharos.reservation.reservations.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.domain.ReservationState;
import spharos.reservation.reservations.dto.ChangeReservationRequest;
import spharos.reservation.reservations.dto.CreateReservationDto;
import spharos.reservation.reservations.dto.ReservationListResponse;

public interface ReservationService {


    // 서비스 신청
    String createReservation(CreateReservationDto reservationNewServiceRequest);

    void changeReservationStatus(ChangeReservationRequest request);

    List<ReservationListResponse>  findWaitReservationsList(Long clientEmail);

    void reservationStatusChange(ReservationState reservationState, String reservationNum);

    void processReservationDecisionEvent(String consumerRecord) throws JsonProcessingException;

}