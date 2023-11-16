package spharos.reservation.reservations.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.reservation.reservations.domain.ReservationGoods;

import java.util.Optional;

public interface ReservationGoodsRepository extends JpaRepository<ReservationGoods, Long> {

    Optional<ReservationGoods> findById(Long id);
}
