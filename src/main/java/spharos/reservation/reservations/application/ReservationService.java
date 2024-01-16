package spharos.reservation.reservations.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import spharos.reservation.reservations.application.dto.ReservationResponse;
import spharos.reservation.reservations.dto.ChangeReservationRequest;
import spharos.reservation.reservations.dto.CreateReservationDto;
import spharos.reservation.reservations.dto.ReservationListResponse;

public interface ReservationService {



    //void checkWorkerAvailability(Long workerId, LocalDate reservationDate, LocalTime serviceStart);
    void verifyPayment(String orderId, int amount);

    // 예약 생성
//    ReservationResponse createReservationCommand(String paymentKey, String orderId, int amount,
//                                  Long serviceId, Long workerId, String userEmail,
//                                  LocalDate reservationDate, String request, String address,
//                                  String clientEmail, LocalTime serviceStart, List<Long> reservationGoodsId);

    // 서비스 신청(결제 요청 - 인증)
   /* ReservationResponse saveWorkTimeAndReservationAndPayment(String paymentKey, String orderId, int amount,
                                                             Long serviceId, Long workerId, String userEmail,
                                                             LocalDate reservationDate, String request, String address,
                                                             String clientEmail, LocalTime serviceStart, List<Long> reservationGoodsId
            , int suppliedAmount, int vat, String status, String method, String approvedAt);
*/
    // 예약 상태 변경
    void changeReservationStatus(ChangeReservationRequest request);

    // 클라이언트 별 예약 대기 상태 목록 조회
    List<ReservationListResponse>  findWaitReservationsList(Long serviceId);

    // 예약 상태 변경
    void processReservationDecisionEvent(String consumerRecord) throws JsonProcessingException;


}