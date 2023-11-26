package spharos.reservation.reservations.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import spharos.reservation.global.common.response.BaseResponse;
import spharos.reservation.reservations.application.UserReservationService;
import spharos.reservation.reservations.vo.request.ReservationInfoForReviewRequest;
import spharos.reservation.reservations.vo.response.ReservationInfoForReviewResponse;
import spharos.reservation.reservations.vo.response.UserRecentServiceResponse;
import spharos.reservation.reservations.vo.response.UserReservationDetailResponse;
import spharos.reservation.reservations.vo.response.UserReservationResponse;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
public class UserReservationController {

    private final UserReservationService userReservationService;

    /*
        최근 받은 서비스 조회
    */
    @Operation(summary = "최근 받은 서비스 조회",
            description = "메인페이지의 최근 받은 서비스 조회를 위해 서비스id리스트를 리턴함",
            tags = { "Main Page" })
    @GetMapping("/recent")
    public BaseResponse<?> getRecentService(@RequestHeader("email") String email) {

        List<Long> serviceIdList = userReservationService.getUserRecentService(email);

        UserRecentServiceResponse response = UserRecentServiceResponse.builder()
                .serviceIdList(serviceIdList)
                .build();

        return new BaseResponse<>(response);
    }

    /*
        리뷰의 예약정보 조회
    */
    @Operation(summary = "리뷰의 예약정보 조회",
            description = "리뷰의 예약정보 조회",
            tags = { "Review" })
    @PostMapping("/review")
    public BaseResponse<?> getReservationForReview(@RequestBody ReservationInfoForReviewRequest request) {

        List<ReservationInfoForReviewResponse> response =
                userReservationService.getReservationForReview(request.getReservationNum());

        return new BaseResponse<>(response);
    }

    /*
        서비스내역 리스트 조회
    */
    @Operation(summary = "서비스내역 리스트 조회",
            description = "서비스내역 리스트 조회",
            tags = { "User Mypage Service History" })
    @GetMapping("/list")
    public BaseResponse<?> getUserReservationList(@RequestHeader("email") String email,
                                                    @RequestParam("state") String state,
                                                    Pageable pageable) {

        // 서비스내역 리스트 조회
        UserReservationResponse response = userReservationService.getUserReservationList(email, state, pageable);
        return new BaseResponse<>(response);
    }

    /*
        서비스 상세 내역조회
    */
    @Operation(summary = "서비스 상세 내역조회",
            description = "서비스 상세 내역조회",
            tags = { "User Mypage Service History" })
    @GetMapping("/detail/{reservationNum}")
    public BaseResponse<?> getUserReservationDetail(@RequestHeader("email") String email,
                                                    @PathVariable("reservationNum") String reservationNum) {

        // 서비스 상세 내역조회
        UserReservationDetailResponse response = userReservationService.getUserReservationDetail(email, reservationNum);
        return new BaseResponse<>(response);
    }

}
