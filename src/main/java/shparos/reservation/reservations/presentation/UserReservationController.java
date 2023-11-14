package shparos.reservation.reservations.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shparos.reservation.global.common.response.BaseResponse;
import shparos.reservation.reservations.application.ReservationService;
import shparos.reservation.reservations.vo.response.UserRecentServiceResponse;

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


}
