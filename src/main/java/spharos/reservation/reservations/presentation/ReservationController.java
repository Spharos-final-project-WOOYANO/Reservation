package spharos.reservation.reservations.presentation;

import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import spharos.reservation.global.common.response.BaseResponse;
import spharos.reservation.global.common.response.ResponseCode;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.reservations.application.ReservationService;
import spharos.reservation.reservations.dto.ChangeReservationRequest;
import spharos.reservation.reservations.dto.CreateReservationDto;
import spharos.reservation.reservations.dto.ReservationListResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;


    /*@Operation(summary = "결제 승인",
            description = "토스 api로 통신")
    @GetMapping("/success")
    public BaseResponse<?> reservationApproveService(  @RequestParam(name = "serviceId") Long serviceId,
                                                       @RequestParam(name = "workerId") Long workerId,
                                                       @RequestParam(name = "userEmail") String userEmail,
                                                       @RequestParam(name = "reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reservationDate,
                                                       @RequestParam(name = "request") String request,
                                                       @RequestParam(name = "address") String address,
                                                       @RequestParam(name = "clientEmail") String clientEmail,
                                                       @RequestParam(name = "orderId") String orderId,
                                                       @RequestParam(name = "paymentKey") String paymentKey,
                                                       @RequestParam(name = "amount") int amount,
                                                       @RequestParam(name = "serviceStart") @DateTimeFormat(pattern = "HH:mm") LocalTime serviceStart,
                                                       @RequestParam(name = "reservationGoodsId") List<Long> reservationGoodsId) {

        reservationService.createReservationCommand(paymentKey, orderId, amount, serviceId, workerId, userEmail,
                reservationDate, request, address, clientEmail, serviceStart, reservationGoodsId);

        //return ResponseEntity.ok(paymentResponse);
        return new BaseResponse<>();
    }*/
    @Operation(summary = "결제 후 예약 상태 변경",
            description = "결제 후 예약 상태 변경")
    @PostMapping("/change")
    public BaseResponse<?> reservationChangeService(@RequestBody ChangeReservationRequest request) {

        reservationService.changeReservationStatus(request);

        return new BaseResponse<>();
    }


    //clientEmail로 예약 대기인거 조회 리스트
    @GetMapping("/wait-list")
    public   List<ReservationListResponse>  getReservationList(@RequestParam Long serviceId) {
        return reservationService.findWaitReservationsList(serviceId);

    }

}
