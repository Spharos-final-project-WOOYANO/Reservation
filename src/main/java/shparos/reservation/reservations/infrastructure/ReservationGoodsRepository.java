package shparos.reservation.reservations.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import shparos.reservation.reservations.domain.ReservationGoods;

public interface ReservationGoodsRepository extends JpaRepository<ReservationGoods, Long> {

}
