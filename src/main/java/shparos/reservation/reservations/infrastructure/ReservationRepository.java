package shparos.reservation.reservations.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import shparos.reservation.reservations.domain.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>  {

//    @Query(value = "SELECT "
//            + "distinct serviceId "
//            + "FROM ReservationManage "
//            + "WHERE userEmail = :email "
//            + "AND reservationState = 4 "
//            + "ORDER BY id DESC LIMIT 8 ")
//    List<Long> findUserRecentReservation(@Param("email") String email);

    List<Reservation> findByUserEmailOrderByIdDesc(String email);

}
