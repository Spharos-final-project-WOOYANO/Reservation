package spharos.reservation.reservations.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.reservation.global.common.response.BaseResponse;
import spharos.reservation.reservations.application.ClientReservationService;
import spharos.reservation.reservations.vo.reqeust.ReservationGoodsRegisterRequest;
import spharos.reservation.reservations.vo.response.ReservationGoodsListResponse;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
public class ClientReservationController {

    private final ClientReservationService clientReservationService;

    /*
        서비스 상품 리스트 조회
    */
    @Operation(summary = "서비스 상품 리스트 조회",
            description = "서비스 상품 리스트 조회",
            tags = { "Client Mypage" })
    @GetMapping("/service/item/list/{serviceId}")
    public BaseResponse<?> getReservationGoodsList(@PathVariable("serviceId") Long serviceId) {

        // 서비스 상품 리스트 조회
        List<ReservationGoodsListResponse> response = clientReservationService.getReservationGoodsList(serviceId);
        return new BaseResponse<>(response);
    }

    /*
        서비스 상품 등록
    */
    @Operation(summary = "서비스 상품 등록",
            description = "서비스 상품 등록",
            tags = { "Client Mypage" })
    @PostMapping("/service/item")
    public BaseResponse<?> registerServiceItem(@RequestBody ReservationGoodsRegisterRequest request) {

        // 서비스 상품 등록
        clientReservationService.registerReservationGoods(request);
        return new BaseResponse<>();
    }

    /*
        서비스 상품 삭제
    */
    @Operation(summary = "서비스 상품 삭제",
            description = "서비스 상품 삭제",
            tags = { "Client Mypage" })
    @DeleteMapping("/service/item/{serviceId}")
    public BaseResponse<?> deleteServiceItem(@PathVariable("serviceId") Long serviceId) {

        // 서비스 상품 삭제
        clientReservationService.deleteReservationGoods(serviceId);
        return new BaseResponse<>();
    }

}
