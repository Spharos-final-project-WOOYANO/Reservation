package spharos.reservation.reservations.axon.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import spharos.reservation.reservations.axon.event.ChangeReservationStatusEvent;
import spharos.reservation.reservations.axon.event.ReservationCreateEvent;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.domain.ReservationGoods;
import spharos.reservation.reservations.domain.ReservationState;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;
import spharos.reservation.reservations.infrastructure.ReservationRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationEventHandler {
    private final ReservationRepository reservationRepository;
    private final ReservationGoodsRepository reservationGoodsRepository;

    @EventHandler
    public void saveOrder(ReservationCreateEvent event) {
        log.info("service id={}", event.getServiceId());
        //예약 중복
      //  Optional<Reservation> reservation = reservationRepository.findByReservationGoodsId(event.getServiceId());

       /* if (reservation.isPresent()) {
            throw new CustomException(ResponseCode.DUPLICATED_RESERVATION);
        }*/
            log.info("[saveOrder]");
            ReservationGoods reservationGoods = reservationGoodsRepository.findById(event.getReservationGoodsId())
                    .get();
            Reservation build = Reservation.builder()
                    .reservationGoods(reservationGoods)
                    .userEmail(event.getUserEmail())
                    .serviceId(event.getServiceId())
                    .workerId(event.getWorkerId())
                    .reservationDate(event.getReservationDate())
                    .serviceStart(event.getServiceStart())
                    .serviceEnd(event.getServiceEnd())
                    .reservationState(ReservationState.PAYMENT_WAITING)
                    .paymentAmount(event.getPaymentAmount())
                    .request(event.getRequest())
                    .reservationNum(event.getReservationNum())
                    .address(event.getAddress())
                    .build();
            reservationRepository.save(build);

    }

    @EventHandler
    public void changeStatusReservation(ChangeReservationStatusEvent event) {
        log.info("[changeStatusReservation]");
        Reservation reservation = reservationRepository.findByReservationNum(event.getReservation_num());
        reservation.changeStatus(event.getStatus());
        reservationRepository.save(reservation);

    }
}
