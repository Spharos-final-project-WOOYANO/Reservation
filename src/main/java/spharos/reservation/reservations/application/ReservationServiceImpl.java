package spharos.reservation.reservations.application;

import static spharos.reservation.global.common.response.ResponseCode.PAYMENT_AMOUNT_MISMATCH;
import static spharos.reservation.reservations.domain.enumPackage.ReservationStatus.WAIT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.reservation.global.config.redis.RedisService;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.reservations.axon.command.ChangeReservationStatusCommand;
import spharos.reservation.reservations.domain.enumPackage.ReservationStatus;
import spharos.reservation.reservations.dto.ChangeReservationRequest;
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
    private final RedisService redisService;



    public void verifyPayment(String orderId, int amount) {
        String values = redisService.getValues(orderId);
        int saveAmount= Integer.parseInt(values);
        if (!Objects.equals(saveAmount, amount)) {
            throw new CustomException(PAYMENT_AMOUNT_MISMATCH);
        }
    }


 /*   public ReservationResponse createReservationCommand(String paymentKey, String orderId, int amount,
                                                        Long serviceId, Long workerId, String userEmail,
                                                        LocalDate reservationDate, String request, String address,
                                                        String clientEmail, LocalTime serviceStart,
                                                        List<Long> reservationGoodsId) {

        CreateReservationCommand createReservationCommand = new CreateReservationCommand(paymentKey, orderId, amount,
                serviceId, workerId, userEmail, reservationDate, request, address, clientEmail, serviceStart, reservationGoodsId);

        commandGateway.send(createReservationCommand);

    }*/







    @Override
    public void changeReservationStatus(ChangeReservationRequest request) {
        ChangeReservationStatusCommand changeReservationStatusCommand = new ChangeReservationStatusCommand
                (request.getReservation_num(), WAIT,request.getClientEmail(),request.getPaymentType(),
                        request.getTotalAmount(),request.getApprovedAt(),request.getPaymentStatus(),request.getPaymentKey());

        commandGateway.send(changeReservationStatusCommand);

    }

    @Override
    public List<ReservationListResponse>  findWaitReservationsList(Long serviceId) {
        return  null;
                /*reservationRepository.findByReservationStatusWait(serviceId, WAIT)
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
                .collect(Collectors.toList());*/

    }



    @Transactional
    @Override
    public void processReservationDecisionEvent(String consumerRecord) throws JsonProcessingException {
        ReservationDecision reservationDecision = objectMapper.readValue(consumerRecord, ReservationDecision.class);
        String reservationNum = reservationDecision.getReservationNum();
        String reservationState = reservationDecision.getReservationState();
        ReservationStatus reservationState1 = ReservationStatus.fromValue(reservationState);
        log.info("reservationNum={}", reservationNum);
        log.info("reservationState1={}", reservationState1);
       // Reservation byReservationNumOne = reservationRepository.findByReservationNumOne(reservationNum);
       // byReservationNumOne.changeStatus(reservationState1);
    }





}