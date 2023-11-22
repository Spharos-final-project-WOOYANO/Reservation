package spharos.reservation.reservations.application;

import static spharos.reservation.reservations.domain.ReservationState.PAYMENT_WAITING;
import static spharos.reservation.reservations.domain.ReservationState.WAIT;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import spharos.reservation.reservations.axon.command.ChangeReservationStatusCommand;
import spharos.reservation.reservations.axon.command.CreateReservationCommand;
import spharos.reservation.reservations.domain.ReservationGoods;
import spharos.reservation.reservations.dto.ChangeReservationRequest;
import spharos.reservation.reservations.dto.CreateReservationDto;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationGoodsRepository reservationGoodsRepository;
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;


    private int randomStrLen= 10;

    @Override
    public void createReservation(CreateReservationDto request) {
        log.info("request reservation good={}", request.getReservationGoodsId().getClass());

//        Boolean serviceExists  = queryGateway.query(new ReservationExistQuery(request.getReservationGoodsId()), Boolean.class).join();
//        log.info("serviceExists : {}", serviceExists);
//        if(!serviceExists){

        String reservationNum = generateRandomReservationNum();


        CreateReservationCommand createOrderCommand = new CreateReservationCommand(
                request.getReservationGoodsId(), request.getServiceId(),
                request.getWorkerId(), request.getUserEmail(),
                request.getReservationDate(), request.getServiceStart(),
                request.getServiceEnd(), request.getPaymentAmount(),
                request.getRequest(),reservationNum,
                request.getAddress(),PAYMENT_WAITING);
        commandGateway.send(createOrderCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Failed to create order", throwable);
            }
            else{
                log.info("Order created successfully");
            }
        });}
        /*else{
            throw new CustomException(ResponseCode.DUPLICATED_RESERVATION);
        }*/



    @Override
    public void changeReservationStatus(ChangeReservationRequest request) {
        ChangeReservationStatusCommand changeReservationStatusCommand = new ChangeReservationStatusCommand
                (request.getReservation_num(), WAIT,request.getClientEmail(),request.getPaymentType(),
                        request.getTotalAmount(),request.getApprovedAt(),request.getPaymentStatus());

        commandGateway.send(changeReservationStatusCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Failed to change reservation status", throwable);
            }
            else{
                log.info("Reservation status changed successfully");
            }
        });

    }

    @Override
    public void test(Long id) {
        log.info("id={}",id);
        //List<ReservationGoods> all = reservationGoodsRepository.findAll();
        ReservationGoods reservationGoods = reservationGoodsRepository.findByTest(4).get();
        log.info("reservationGoods={}",reservationGoods);
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