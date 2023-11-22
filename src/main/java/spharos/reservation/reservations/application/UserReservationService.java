package spharos.reservation.reservations.application;

import org.springframework.data.domain.Pageable;
import spharos.reservation.reservations.vo.response.ReservationInfoForReviewResponse;
import spharos.reservation.reservations.vo.response.UserReservationDetailResponse;
import spharos.reservation.reservations.vo.response.UserReservationResponse;

import java.util.List;

public interface UserReservationService {

    // 최근 받은 서비스 조회
    List<Long> getUserRecentService(String email);
    // 리뷰의 예약정보 조회
    List<ReservationInfoForReviewResponse> getReservationForReview(String reservationNum);
    // 서비스내역 리스트 조회
    UserReservationResponse getUserReservationList(String email, String state, Pageable pageable);
    // 서비스 상세 내역조회
    UserReservationDetailResponse getUserReservationDetail(String email, String reservationNum);
}
