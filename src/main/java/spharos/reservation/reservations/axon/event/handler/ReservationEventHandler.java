package spharos.reservation.reservations.axon.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
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
        log.info("[saveOrder]");
        ReservationGoods reservationGoods = reservationGoodsRepository.findById(event.getReservationGoodsId()).get();
        Reservation build = Reservation.builder()
                .reservationGoods(reservationGoods)
                .userEmail(event.getUserEmail())
                .serviceId(event.getServiceId())
                .workerId(event.getWorkerId())
                .reservationDate(event.getReservationDate())
                .serviceStart(event.getServiceStart())
                .serviceEnd(event.getServiceEnd())
                .reservationState(ReservationState.PAVEMENT_WAITING)
                .paymentAmount(event.getPaymentAmount())
                .request(event.getRequest())
                .reservationNum(event.getReservationNum())
                .address(event.getAddress())
                .build();
        reservationRepository.save(build);
    }


}
