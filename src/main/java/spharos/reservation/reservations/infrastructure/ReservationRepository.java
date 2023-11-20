package spharos.reservation.reservations.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spharos.reservation.reservations.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>  {

//    @Query(value = "SELECT "
//            + "distinct serviceId "
//            + "FROM ReservationManage "
//            + "WHERE userEmail = :email "
//            + "AND reservationState = 4 "
//            + "ORDER BY id DESC LIMIT 8 ")
//    List<Long> findUserRecentReservation(@Param("email") String email);

    @Query(value = "SELECT r FROM Reservation r WHERE r.reservationGoods.id = :reservationGoods")
    Optional<Reservation> findByReservationGoodsId(@Param("reservationGoods") Long reservationGoods);

    List<Reservation> findByUserEmailOrderByIdDesc(String email);
   Reservation findByReservationNum(String reservationNum);


}
