package spharos.reservation.reservations.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.reservation.global.common.response.ResponseCode;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.infrastructure.ReservationRepository;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewWorkerServiceImpl implements ReviewWorkerService{

    private final ReservationRepository reservationRepository;
    @Override
    public Long getWorkerIdList(String reservationNum) {

        return reservationRepository.findByReservationNum(reservationNum).stream()
                // ↓ 리스트에서 null이 아닌 값만 필터링한다.
                .map(Reservation::getWorkerId)
                .filter(Objects::nonNull)
                .findFirst().orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_RESERVATION_WORKER));
    }
}
