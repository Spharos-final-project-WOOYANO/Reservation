package spharos.reservation.reservations.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import spharos.reservation.reservations.dto.ChangeReservationRequest;
import spharos.reservation.reservations.dto.CreateReservationDto;
import spharos.reservation.reservations.dto.ReservationListResponse;

public interface ReservationService {


    // 서비스 신청
    String createReservation(CreateReservationDto reservationNewServiceRequest);

    // 예약 상태 변경
    void changeReservationStatus(ChangeReservationRequest request);

    // 클라이언트 별 예약 대기 상태 목록 조회
    List<ReservationListResponse>  findWaitReservationsList(Long serviceId);

    // 예약 상태 변경
    void processReservationDecisionEvent(String consumerRecord) throws JsonProcessingException;


}