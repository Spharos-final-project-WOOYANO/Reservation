package spharos.reservation.reservations.application;

import spharos.reservation.reservations.dto.ChangeReservationRequest;
import spharos.reservation.reservations.dto.CreateReservationDto;

public interface ReservationService {


    // 서비스 신청
    String createReservation(CreateReservationDto reservationNewServiceRequest);

    void changeReservationStatus(ChangeReservationRequest request);

}