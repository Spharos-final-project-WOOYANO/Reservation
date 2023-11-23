package spharos.reservation.reservations.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.reservation.global.common.response.ResponseCode;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.reservations.domain.ReservationGoods;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;
import spharos.reservation.reservations.vo.reqeust.ReservationGoodsRegisterRequest;
import spharos.reservation.reservations.vo.response.ReservationGoodsListResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientReservationServiceImpl implements ClientReservationService{

    private final ReservationGoodsRepository reservationGoodsRepository;

    // 서비스 상품 리스트 조회
    @Override
    public List<ReservationGoodsListResponse> getReservationGoodsList(Long serviceId) {

        // 서비스Id로 등록된 서비스 상품 리스트를 조회
        List<ReservationGoods> reservationGoodsList = reservationGoodsRepository.findByServiceIdOrderByIdDesc(serviceId);

        // 등록된 서비스 상품이 없는경우 null을 리턴
        if(reservationGoodsList.isEmpty()) {
            return null;
        }

        List<ReservationGoodsListResponse> responseList = new ArrayList<>();
        for(ReservationGoods reservationGoods : reservationGoodsList) {
            ReservationGoodsListResponse response = ReservationGoodsListResponse.builder()
                    .goodsId(reservationGoods.getId())
                    .serviceItemName(reservationGoods.getServiceItemName())
                    .createdAt(reservationGoods.getCreatedAt())
                    .serviceTime(reservationGoods.getServiceTime())
                    .price(reservationGoods.getPrice())
                    .build();
            responseList.add(response);
        }
        return responseList;
    }

    // 서비스 상품 등록
    @Override
    @Transactional
    public void registerReservationGoods(ReservationGoodsRegisterRequest request) {

        // 서비스 상품 등록
        ReservationGoods reservationGoods = ReservationGoods.createReservationGoods(
                request.getServiceId(), request.getServiceItemName(), request.getPrice(), request.getServiceTime(),
                request.getSuperCategory(), request.getBaseCategory(), request.getSubCategory());
        reservationGoodsRepository.save(reservationGoods);
    }

    // 서비스 상품 삭제
    @Override
    @Transactional
    public void deleteReservationGoods(Long goodsId) {

        // 삭제할 서비스 상품을 조회
        ReservationGoods reservationGoods = reservationGoodsRepository.findById(goodsId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_RESERVATION_GOODS));

        reservationGoodsRepository.delete(reservationGoods);
    }
}
