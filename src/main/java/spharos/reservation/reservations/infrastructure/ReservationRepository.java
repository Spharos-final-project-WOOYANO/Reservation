package spharos.reservation.reservations.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.reservation.reservations.domain.Reservation;
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

    List<Reservation> findByReservationNum(String reservationNum);

    List<Long> findAllWorkerIdByReservationNum(String reservationNum);



}
