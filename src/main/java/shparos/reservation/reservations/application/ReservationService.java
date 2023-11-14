package shparos.reservation.reservations.application;

import java.util.List;

public interface ReservationService {

    // 최근 받은 서비스 조회
    List<Long> getUserRecentService(String email);


}
