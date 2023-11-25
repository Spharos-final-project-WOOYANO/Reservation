package spharos.reservation.reservations.axon.event.handler;

import java.util.Optional;
import lombok.AllArgsConstructor;
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
import spharos.reservation.reservations.domain.ReservationState;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;
import spharos.reservation.reservations.infrastructure.ReservationRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("reserva")
public class ReservationEventHandler {
    private final ReservationRepository reservationRepository;
    private final ReservationGoodsRepository reservationGoodsRepository;

    @EventHandler
    public void create( ReservationCreateEvent event) {
        log.info("reservation_goods id={}", event.getServiceId());
        //예약 중복
        Optional<Reservation> reservation = reservationRepository.findByReservationGoodsId(event.getReservationGoodsId());

        if (reservation.isPresent()) {
            throw new CustomException(ResponseCode.DUPLICATED_RESERVATION);
        }
        log.info("[saveOrder]");
        log.info("getReservationGoodsId={}", event.getReservationGoodsId());
        ReservationGoods reservationGoods = reservationGoodsRepository.findById(event.getReservationGoodsId()).get();
        log.info("reservationGoodsgetId={}", reservationGoods.getId());
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
        Reservation reservation = reservationRepository.findByReservationNumOne(event.getReservation_num());
        reservation.changeStatus(event.getStatus());
        reservationRepository.save(reservation);

    }

    @EventHandler
    public void cancel(CancelReservationStatusEvent event){
        log.info("[cancel]");
        Reservation byReservationNumOne = reservationRepository.findByReservationNumOne(event.getReservation_num());
        reservationRepository.delete(byReservationNumOne);

    }
}

