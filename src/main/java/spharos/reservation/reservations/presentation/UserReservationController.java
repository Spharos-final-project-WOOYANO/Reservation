package spharos.reservation.reservations.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.reservation.dto.NewReservationDto;
import spharos.reservation.global.common.response.BaseResponse;
import spharos.reservation.reservations.application.ReservationService;
import spharos.reservation.reservations.vo.request.ReservationInfoForReviewRequest;
import spharos.reservation.reservations.vo.request.ReservationNewServiceRequest;
import spharos.reservation.reservations.vo.response.ReservationInfoForReviewResponse;
import spharos.reservation.reservations.vo.response.UserRecentServiceResponse;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
public class UserReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "최근 받은 서비스 조회",
            description = "메인페이지의 최근 받은 서비스 조회를 위해 서비스id리스트를 리턴함",
            tags = { "Main Page" })
    @GetMapping("/recent")
    public BaseResponse<?> getRecentService(@RequestHeader("email") String email) {

        List<Long> serviceIdList = reservationService.getUserRecentService(email);

        UserRecentServiceResponse response = UserRecentServiceResponse.builder()
                .serviceIdList(serviceIdList)
                .build();

        return new BaseResponse<>(response);
    }

    @Operation(summary = "리뷰의 예약정보 조회",
            description = "리뷰의 예약정보 조회",
            tags = { "Review" })
    @GetMapping("/review")
    public BaseResponse<?> getReservationForReview(@RequestBody ReservationInfoForReviewRequest request) {

        List<ReservationInfoForReviewResponse> response =
                reservationService.getReservationForReview(request.getReservationNum());

        return new BaseResponse<>(response);
    }

    @Operation(summary = "서비스 신청",
            description = "유저의 서비스 신청",
            tags = { "NewReservation" })
    @PostMapping("/new")
    public BaseResponse<?> reservationNewService(@RequestBody ReservationNewServiceRequest request) {

        NewReservationDto newReservationDto = NewReservationDto.builder()
                .reservationGoodsId(request.getReservationGoodsId())
                .serviceId(request.getServiceId())
                .workerId(request.getWorkerId())
                .userEmail(request.getUserEmail())
                .reservationDate(request.getReservationDate())
                .serviceStart(request.getServiceStart())
                .serviceEnd(request.getServiceEnd())
                .paymentAmount(request.getPaymentAmount())
                .request(request.getRequest())
                .address(request.getAddress())
                .build();

        reservationService.createNewReservation(newReservationDto);

        return new BaseResponse<>();
    }

}
