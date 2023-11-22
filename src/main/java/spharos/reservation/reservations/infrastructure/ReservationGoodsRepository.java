package spharos.reservation.reservations.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.reservation.reservations.domain.ReservationGoods;

public interface ReservationGoodsRepository extends JpaRepository<ReservationGoods, Long> {

    @Query("select r from ReservationGoods r where r.id = :id")
    Optional<ReservationGoods> findByTest(@Param("id") int id);
}
