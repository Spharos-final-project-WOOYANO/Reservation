package spharos.reservation.reservations.axon.event.handler;

import static spharos.reservation.global.common.response.ResponseCode.CANNOT_FIND_RESERVATION_GOODS;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import spharos.reservation.global.common.response.ResponseCode;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.reservations.axon.event.CancelReservationStatusEvent;
import spharos.reservation.reservations.axon.event.ChangeReservationStatusEvent;
import spharos.reservation.reservations.axon.event.ReservationCreateEvent;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.domain.ReservationGoods;
import spharos.reservation.reservations.domain.enumPackage.ReservationStatus;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;
import spharos.reservation.reservations.infrastructure.ReservationRepository;
import spharos.reservation.reservations.presentation.ReservationController;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("reserva")
public class ReservationEventHandler {
    private final ReservationRepository reservationRepository;
    private final ReservationGoodsRepository reservationGoodsRepository;

    private final ReservationController sseController;
    @EventHandler
    public void create(ReservationCreateEvent event) {
        log.info("[saveReservation]");
        List<ReservationGoods> reservationGoods = reservationGoodsRepository.findByIdIn(event.getReservationGoodsId());
        Reservation reservations = Reservation.createReservation(reservationGoods, event.getUserEmail(),
                event.getServiceId(), event.getWorkerId(), event.getReservationDate(), event.getServiceStart(),
                event.getAmount(),null, event.getRequest(), event.getAddress(), event.getOrderId(),event.getApprovedAt());
        reservationRepository.save(reservations);


    }

    @EventHandler
    public void changeStatusReservation(ChangeReservationStatusEvent event) {

    }

    @EventHandler
    public void cancel(CancelReservationStatusEvent event){

    }
}

