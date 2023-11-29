package spharos.reservation.reservations.axon.event.handler;

import static spharos.reservation.global.common.response.ResponseCode.CANNOT_FIND_RESERVATION_GOODS;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import spharos.reservation.global.SseEmitters;
import spharos.reservation.global.common.response.ResponseCode;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.reservations.axon.event.CancelReservationStatusEvent;
import spharos.reservation.reservations.axon.event.ChangeReservationStatusEvent;
import spharos.reservation.reservations.axon.event.ReservationCreateEvent;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.domain.ReservationGoods;
import spharos.reservation.reservations.domain.enumPackage.ReservationState;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;
import spharos.reservation.reservations.infrastructure.ReservationRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("reserva")
public class ReservationEventHandler {
    private final ReservationRepository reservationRepository;
    private final ReservationGoodsRepository reservationGoodsRepository;
    private final SseEmitters sseEmitters;
    @EventHandler
    public void create( ReservationCreateEvent event) {
        log.info("[saveReservation]");
        //예약 중복
        List<Long> reservationGoodsIdList = event.getReservationGoodsId();
        Long workerId = event.getWorkerId();

        boolean isDuplicate = reservationGoodsIdList.stream()
                .anyMatch(reservationGoodsId ->
                        reservationRepository.findByReservationGoodsId(reservationGoodsId, workerId)
                                .isPresent());

        if (isDuplicate) {
            throw new CustomException(ResponseCode.DUPLICATED_RESERVATION);
        }


        // 중복이 없으면 예약 생성
        reservationGoodsIdList.stream()
                .map(reservationGoodsId -> {
                    log.info("[saveOrder]");
                    ReservationGoods reservationGoods = reservationGoodsRepository.findById(reservationGoodsId)
                            .orElseThrow(() -> new CustomException(CANNOT_FIND_RESERVATION_GOODS));

                    return Reservation.builder()
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
                })
                .forEach(reservationRepository::save);


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
        //프론트한테 PaymentKey 보내기-> 프론트가 PaymentKey로 결제 취소 요청
        SseEmitter emitter = new SseEmitter();
        sseEmitters.add(emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name("cancel")
                    .data("{\"paymentKey\": \"" + event.getPaymentKey() + "\"}"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

