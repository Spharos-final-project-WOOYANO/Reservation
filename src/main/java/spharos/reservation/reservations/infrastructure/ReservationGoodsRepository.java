package spharos.reservation.reservations.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import spharos.reservation.reservations.domain.ReservationGoods;



public interface ReservationGoodsRepository extends JpaRepository<ReservationGoods, Long> {

    Optional<ReservationGoods> findById(Long id);
    List<ReservationGoods> findByServiceIdOrderByIdDesc(Long serviceId);


}
