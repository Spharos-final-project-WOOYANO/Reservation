package spharos.reservation.reservations.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.reservation.dto.NewReservationDto;
import spharos.reservation.global.common.response.ResponseCode;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.global.utill.AppConfig;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.domain.ReservationGoods;
import spharos.reservation.reservations.domain.ReservationState;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;
import spharos.reservation.reservations.infrastructure.ReservationRepository;
import spharos.reservation.reservations.vo.response.ReservationInfoForReviewResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationManageRepository;
    private final ReservationGoodsRepository reservationGoodsRepository;
    // 예약번호 생성
    private final AppConfig appConfig;
    // 최근 받은 서비스 조회
    @Override
    public List<Long> getUserRecentService(String email) {

        // 유저 이메일로 최신 예약 조회
        List<Reservation> reservationList =
                reservationManageRepository.findByUserEmailOrderByIdDesc(email);

        // 예약정보가 없는 경우에는 null을 리턴
        if(reservationList.isEmpty()) {
            return null;
        }

        List<Long> serviceIdList = new ArrayList<>();

        Long serviceId = 0L;
        int count = 0;
        // 예약정보가 있는 경우, 예약번호가 동일하면 제외하고 8개만 리턴
        for(Reservation reservation : reservationList) {

            if(count > 7) {
                break;
            }

            // 서비스id가 같거나 서비스상태가 서비스완료가 아닌경우는 continue
            if(serviceId.equals(reservation.getServiceId()) ||
                    !reservation.getReservationState().equals(ReservationState.COMPLETE)) {
                continue;
            }
            // 서비스id리스트에 추가
            serviceIdList.add(reservation.getServiceId());
            // 비교할 예약번호에 예약번호를 셋팅
            serviceId = reservation.getServiceId();
            // 카운트 증가
            count++;
        }

        return serviceIdList;
    }

    // 리뷰의 예약정보 조회
    @Override
    public List<ReservationInfoForReviewResponse> getReservationForReview(String reservationNum) {

        // 예약번호로 예약정보조회
        List<Reservation> reservationList = reservationManageRepository.findByReservationNum(reservationNum);

        // 예약번호로 조회된 예약정보가 없는 경우 에러
        if(reservationList.isEmpty()) {
            throw new CustomException(ResponseCode.CANNOT_FIND_RESERVATION);
        }

        List<ReservationInfoForReviewResponse> responseList = new ArrayList<>();
        for(Reservation reservation : reservationList) {
            ReservationInfoForReviewResponse response = ReservationInfoForReviewResponse.builder()
                    .serviceItemName(reservation.getReservationGoods().getServiceItemName())
                    .reservationDate(reservation.getReservationDate())
                    .workerId(reservation.getWorkerId())
                    .build();
            responseList.add(response);
        }
        return responseList;
    }

    //예약 신청
    @Override
    public void createNewReservation(NewReservationDto reservationDto){

        //예약 상품이 존재하지 않을경우
        Optional<ReservationGoods> reservationGoods = reservationGoodsRepository.findById(reservationDto.getReservationGoodsId());

        if(reservationGoods.isEmpty()){
            log.info("reservationGoods is empty");
            throw new CustomException(ResponseCode.CANNOT_FIND_RESERVATION_GOODS);
        }

        ReservationGoods reservationServiceGoods = reservationGoods.get();

        ReservationState reservationState = ReservationState.WAIT;

        String reservationNumber = "WYN-" + AppConfig.getRandomValue();
        log.info("reservationNumber : {}", reservationNumber);
        Reservation reservation = Reservation.createReservation(
                        reservationServiceGoods,
                        reservationDto.getUserEmail(),
                        reservationDto.getServiceId(),
                        reservationDto.getWorkerId(),
                        reservationDto.getReservationDate(),
                        reservationDto.getServiceStart(),
                        reservationDto.getServiceEnd(),
                        reservationState,
                        reservationDto.getPaymentAmount(),
                        reservationDto.getRequest(),
                        reservationNumber,
                        reservationDto.getAddress());

        reservationManageRepository.save(reservation);

    }
}
