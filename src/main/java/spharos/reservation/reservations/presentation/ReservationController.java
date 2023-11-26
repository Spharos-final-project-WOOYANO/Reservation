package spharos.reservation.reservations.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.reservation.global.common.response.BaseResponse;
import spharos.reservation.global.common.response.ErrorResponse;
import spharos.reservation.global.common.response.ResponseCode;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.reservations.application.ReservationService;
import spharos.reservation.reservations.dto.ChangeReservationRequest;
import spharos.reservation.reservations.dto.CreateReservationDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "서비스 신청",
            description = "유저의 서비스 신청")
    @PostMapping("/create")
    public BaseResponse<?> reservationNewService(@RequestBody CreateReservationDto request) {
        try {
            reservationService.createReservation(request);
            return new BaseResponse<>();
        } catch (CustomException ex) {
            // CustomException이 발생했을 때의 처리
            ResponseCode responseCode = ex.getResponseCode();
            return new BaseResponse<>(responseCode);
        }
        //String reservation = reservationService.createReservation(request);

       // return new BaseResponse<>(reservation);
    }


    @Operation(summary = "결제 후 예약 상태 변경",
            description = "결제 후 예약 상태 변경")
    @PostMapping("/change")
    public BaseResponse<?> reservationChangeService(@RequestBody ChangeReservationRequest request) {

        reservationService.changeReservationStatus(request);

        return new BaseResponse<>();
    }


}
