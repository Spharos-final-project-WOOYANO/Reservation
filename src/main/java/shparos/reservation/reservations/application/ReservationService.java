package shparos.reservation.reservations.application;

import shparos.reservation.reservations.vo.response.ReservationInfoForReviewResponse;

import java.util.List;

public interface ReservationService {

    // 최근 받은 서비스 조회
    List<Long> getUserRecentService(String email);
    // 리뷰의 예약정보 조회
    List<ReservationInfoForReviewResponse> getReservationForReview(String reservationNum);


}
