package spharos.reservation.reservations.application;

import spharos.reservation.reservations.vo.reqeust.ReservationGoodsRegisterRequest;
import spharos.reservation.reservations.vo.response.ReservationGoodsListResponse;

import java.util.List;

public interface ClientReservationService {

    // 서비스 상품 리스트 조회
    List<ReservationGoodsListResponse> getReservationGoodsList(Long serviceId);
    // 서비스 상품 등록
    void registerReservationGoods(ReservationGoodsRegisterRequest request);
    // 서비스 상품 삭제
    void deleteReservationGoods(Long goodsId);

}
