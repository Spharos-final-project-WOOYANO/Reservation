package spharos.reservation.reservations.application;

import static spharos.reservation.reservations.domain.enumPackage.ReservationState.PAYMENT_WAITING;
import static spharos.reservation.reservations.domain.enumPackage.ReservationState.WAIT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.reservation.reservations.axon.command.ChangeReservationStatusCommand;
import spharos.reservation.reservations.axon.command.CreateReservationCommand;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.domain.enumPackage.ReservationState;
import spharos.reservation.reservations.dto.ChangeReservationRequest;
import spharos.reservation.reservations.dto.CreateReservationDto;
import spharos.reservation.reservations.dto.ReservationDecision;
import spharos.reservation.reservations.dto.ReservationListResponse;
import spharos.reservation.reservations.infrastructure.ReservationRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CommandGateway commandGateway;
    private final ObjectMapper objectMapper;
    private int randomStrLen= 10;

    @Override
    public String createReservation(CreateReservationDto request) {
        log.info("request reservation good={}", request.getReservationGoodsId().getClass());

        String reservationNum = generateRandomReservationNum();

        CreateReservationCommand createOrderCommand = new CreateReservationCommand(
                request.getReservationGoodsId(), request.getServiceId(),
                request.getWorkerId(), request.getUserEmail(),
                request.getReservationDate(), request.getServiceStart(),
                request.getServiceEnd(), request.getPaymentAmount(),
                request.getRequest(), reservationNum,
                request.getAddress(), PAYMENT_WAITING);
        commandGateway.send(createOrderCommand);
        return reservationNum;

    }

    @Override
    public void changeReservationStatus(ChangeReservationRequest request) {
        ChangeReservationStatusCommand changeReservationStatusCommand = new ChangeReservationStatusCommand
                (request.getReservation_num(), WAIT,request.getClientEmail(),request.getPaymentType(),
                        request.getTotalAmount(),request.getApprovedAt(),request.getPaymentStatus(),request.getPaymentKey());

        commandGateway.send(changeReservationStatusCommand);



    }

    @Override
    public List<ReservationListResponse>  findWaitReservationsList(Long serviceId) {
        return reservationRepository.findByReservationStatusWait(serviceId, WAIT)
                .stream()
                .map(reservation ->
                        ReservationListResponse.builder()
                                .reservationNum(reservation.getReservationNum())
                                .reservationGoods(reservation.getReservationGoods())
                                .serviceId(reservation.getServiceId())
                                .workerId(reservation.getWorkerId())
                                .userEmail(reservation.getUserEmail())
                                .reservationDate(reservation.getReservationDate())
                                .serviceStart(reservation.getServiceStart())
                                .serviceEnd(reservation.getServiceEnd())
                                .paymentAmount(reservation.getPaymentAmount())
                                .request(reservation.getRequest())
                                .reservationState(reservation.getReservationState())
                                .address(reservation.getAddress())
                                .build()
                )
                .collect(Collectors.toList());

    }



    @Transactional
    @Override
    public void processReservationDecisionEvent(String consumerRecord) throws JsonProcessingException {
        ReservationDecision reservationDecision = objectMapper.readValue(consumerRecord, ReservationDecision.class);
        String reservationNum = reservationDecision.getReservationNum();
        String reservationState = reservationDecision.getReservationState();
        ReservationState reservationState1 = ReservationState.fromValue(reservationState);
        log.info("reservationNum={}", reservationNum);
        log.info("reservationState1={}", reservationState1);
        Reservation byReservationNumOne = reservationRepository.findByReservationNumOne(reservationNum);
        byReservationNumOne.changeStatus(reservationState1);
    }



    //랜덤 예약번호 생성
    private String generateRandomReservationNum() {
        Random random = new Random();
        StringBuilder randomBuf = new StringBuilder();
        for (int i = 0; i < randomStrLen; i++) {
            // Random.nextBoolean() : 랜덤으로 true, false 리턴 (true : 랜덤 소문자 영어, false : 랜덤 숫자)
            if (random.nextBoolean()) {
                // 26 : a-z 알파벳 개수
                // 97 : letter 'a' 아스키코드
                // (int)(random.nextInt(26)) + 97 : 랜덤 소문자 아스키코드
                randomBuf.append((char) ((int) (random.nextInt(26)) + 97));
            } else {
                randomBuf.append(random.nextInt(10));
            }
        }
        return randomBuf.toString();
    }


}