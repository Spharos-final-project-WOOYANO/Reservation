package spharos.reservation.reservations.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.reservation.reservations.application.ReviewWorkerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReviewController {

    private final ReviewWorkerService reviewWorkerService;
    @Operation(summary = "작업자 Id찾기",
            description = "유저가 등록한 리뷰의 서비스 작업자 Id찾기",
            tags = { "Find WorkerID" })
    @GetMapping("/review/writer")
    public Long getWorkerIdList(@RequestParam("reservationNum") String reservationNum) {

        return reviewWorkerService.getWorkerIdList(reservationNum);
    }
}
