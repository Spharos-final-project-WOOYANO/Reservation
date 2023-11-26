package spharos.reservation.reservations.axon.event.handler;

import static spharos.reservation.global.common.response.ResponseCode.CANNOT_FIND_RESERVATION_GOODS;

import java.util.List;

import java.util.Optional;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@ProcessingGroup("reservations")
public class ReservationEventHandler {
    private final ReservationRepository reservationRepository;
    private final ReservationGoodsRepository reservationGoodsRepository;

    @EventHandler
    public void create( ReservationCreateEvent event) {
        log.info("reservation_goods id={}", event.getServiceId());
        //예약 중복
        List<Long> reservationGoodsIdList = event.getReservationGoodsId();
        for (Long reservationGoodsId : reservationGoodsIdList) {
            Optional<Reservation> existingReservation = reservationRepository.findByReservationGoodsId(reservationGoodsId);
            if (existingReservation.isPresent()) {
                throw new CustomException(ResponseCode.DUPLICATED_RESERVATION);
            }
        }

/*        Optional<Reservation> reservation = reservationRepository.findByReservationGoodsId(event.getReservationGoodsId());

        if (reservation.isPresent()) {
            throw new CustomException(ResponseCode.DUPLICATED_RESERVATION);
        }*/

// 중복이 없으면 예약 생성
        for (Long reservationGoodsId : reservationGoodsIdList) {
            log.info("[saveOrder]");
            log.info("getReservationGoodsId={}", reservationGoodsId);
            ReservationGoods reservationGoods = reservationGoodsRepository.findById(reservationGoodsId)
                    .orElseThrow(() -> new CustomException(CANNOT_FIND_RESERVATION_GOODS));

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


        ///
     /*   log.info("[saveOrder]");
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
        reservationRepository.save(build);*/

    }

    @EventHandler
    public void changeStatusReservation(ChangeReservationStatusEvent event) {
        log.info("[changeStatusReservation]");
        List<Reservation> reservations = reservationRepository.findByReservationNumList(event.getReservation_num());
        for (Reservation reservation : reservations) {
            reservation.changeStatus(event.getStatus());
            reservationRepository.save(reservation);
        }

    }

    @EventHandler
    public void cancel(CancelReservationStatusEvent event){
        log.info("[cancel]");
        Reservation byReservationNumOne = reservationRepository.findByReservationNumOne(event.getReservation_num());
        reservationRepository.delete(byReservationNumOne);

    }
}