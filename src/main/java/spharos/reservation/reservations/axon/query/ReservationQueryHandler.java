package spharos.reservation.reservations.axon.query;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.infrastructure.ReservationRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationQueryHandler {

    private final ReservationRepository reservationRepository;

    @QueryHandler
    public boolean handle(ReservationExistQuery query){
        Optional<Reservation> reservation = reservationRepository.findByReservationGoodsId(query.getReservationGoodsId());
        log.info("getReservationGoodsId id={}", query.getReservationGoodsId());
        if (reservation.isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }
}
