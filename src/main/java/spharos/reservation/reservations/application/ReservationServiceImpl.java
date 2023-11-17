package spharos.reservation.reservations.application;

import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import spharos.reservation.reservations.axon.command.CreateReservationCommand;
import spharos.reservation.reservations.dto.CreateReservationDto;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;
import spharos.reservation.reservations.infrastructure.ReservationRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationManageRepository;
    private final ReservationGoodsRepository reservationGoodsRepository;
    private final CommandGateway commandGateway;

    private int randomStrLen= 10;

    @Override
    public void createReservation(CreateReservationDto request) {
        Random random = new Random();
        StringBuffer randomBuf = new StringBuffer();
        for (int i = 0; i < randomStrLen; i++) {
            // Random.nextBoolean() : 랜덤으로 true, false 리턴 (true : 랜덤 소문자 영어, false : 랜덤 숫자)
            if (random.nextBoolean()) {
                // 26 : a-z 알파벳 개수
                // 97 : letter 'a' 아스키코드
                // (int)(random.nextInt(26)) + 97 : 랜덤 소문자 아스키코드
                randomBuf.append((char)((int)(random.nextInt(26)) + 97));
            } else {
                randomBuf.append(random.nextInt(10));
            }
        }
        String reservationNum = randomBuf.toString();


        CreateReservationCommand createOrderCommand = new CreateReservationCommand(UUID.randomUUID().toString(),
                request.getReservationGoodsId(), request.getServiceId(),
                request.getWorkerId(), request.getUserEmail(),
                request.getReservationDate(), request.getServiceStart(),
                request.getServiceEnd(), request.getPaymentAmount(),
                request.getRequest(),reservationNum,
                request.getAddress());
        commandGateway.send(createOrderCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Failed to create order", throwable);
            }
            else{
                log.info("Order created successfully");
            }
        });

    }

}