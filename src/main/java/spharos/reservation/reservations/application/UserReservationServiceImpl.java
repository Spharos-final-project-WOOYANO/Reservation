package spharos.reservation.reservations.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spharos.reservation.global.common.response.ResponseCode;
import spharos.reservation.global.exception.CustomException;
import spharos.reservation.reservations.domain.Reservation;
import spharos.reservation.reservations.domain.enumPackage.ReservationState;
import spharos.reservation.reservations.domain.enumPackage.ReservationStateConverter;
import spharos.reservation.reservations.dto.UserReservationDto;
import spharos.reservation.reservations.infrastructure.ReservationGoodsRepository;
import spharos.reservation.reservations.infrastructure.ReservationRepository;
import spharos.reservation.reservations.vo.response.ReservationInfoForReviewResponse;
import spharos.reservation.reservations.vo.response.UserReservationDetailResponse;
import spharos.reservation.reservations.vo.response.UserReservationResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserReservationServiceImpl implements UserReservationService{

    private final ReservationRepository reservationRepository;
    private final ReservationGoodsRepository reservationGoodsRepository;


    // 최근 받은 서비스 조회
    @Override
    public List<Long> getUserRecentService(String email) {

        // 유저 이메일로 최신 예약 조회
        List<Reservation> reservationList =
                reservationRepository.findByUserEmailOrderByIdDesc(email);

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
        List<Reservation> reservationList = reservationRepository.findByReservationNum(reservationNum);

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

    // 서비스내역 리스트 조회
    @Override
    public UserReservationResponse getUserReservationList(String email, String state, Pageable pageable) {

        Page<String> reservationNumList = null;

        if(state.equals("전체")) {
            // 예약번호 기준으로 서비스내역 조회
            reservationNumList = reservationRepository.findUserReservationNumber(email, pageable);
        } else {
            ReservationState reservationState = getReservationState(state);
            reservationNumList = reservationRepository.findUserReservationNumberAndState(email,
                    reservationState, pageable);
        }

        // 예약이 없으면 null을 리턴
        if(reservationNumList.isEmpty()) {
            return null;
        }

        // 예약번호에 따른 예약정보리스트 가져오기
        List<UserReservationDto> userReservationDtoList = new ArrayList<>();
        for(String reservationNum : reservationNumList.getContent()) {

            // 예약번호에 일치하는 예약정보 가져오기
            List<Reservation> reservationList = reservationRepository.findByReservationNum(reservationNum);

            // 상품 표시명 리스트 만들기
            List<String> serviceItemNameList = new ArrayList<>();
            for(Reservation reservation : reservationList) {
                serviceItemNameList.add(reservation.getReservationGoods().getServiceItemName());
            }

            // 상품id와 상품 가격 이외에는 모두 같은 값이므로 리스트에서 제일 첫번째 요소를 가져오기
            Reservation reservation = reservationList.get(0);
            UserReservationDto dto = UserReservationDto.builder()
                    .reservationNum(reservationNum)
                    .serviceType(reservation.getReservationGoods().getSuperCategory())
                    .reservationState(reservation.getReservationState().getValue())
                    .serviceItemNameList(serviceItemNameList)
                    .reservationDate(reservation.getReservationDate())
                    .serviceStart(reservation.getServiceStart())
                    // 리스트의 경우 마지막 요소의 시간을 가져와야함
                    .serviceEnd(reservationList.get(reservationList.size()-1).getServiceEnd())
                    .serviceId(reservation.getServiceId())
                    .workerId(reservation.getWorkerId())
                    .build();
            // dto 리스트에 추가
            userReservationDtoList.add(dto);
        }

        return UserReservationResponse.builder()
                .reservationDtoList(userReservationDtoList)
                .page(reservationNumList.getPageable().getPageNumber())
                .size(reservationNumList.getPageable().getPageSize())
                .totalRows(reservationNumList.getTotalElements())
                .build();
    }

    // 서비스 상세 내역조회
    @Override
    public UserReservationDetailResponse getUserReservationDetail(String email, String reservationNum) {

        // 예약번호에 일치하는 예약정보 가져오기
       List<Reservation> reservationList = reservationRepository.findByReservationNum(reservationNum);

        // 예약번호로 조회된 예약정보가 없는 경우 에러
        if(reservationList.isEmpty()) {
            throw new CustomException(ResponseCode.CANNOT_FIND_RESERVATION);
        }

        // 상품id와 상품 가격 이외에는 모두 같은 값이므로 리스트에서 제일 첫번째 요소를 가져오기
        Reservation reservation = reservationList.get(0);

        // 이메일이 다른 경우는 예외처리
        if(!reservation.getUserEmail().equals(email)) {
            throw new CustomException(ResponseCode.WRONG_APPROACH);
        }

        // 상품 표시명 리스트와 가격 총합 만들기
        List<String> serviceItemNameList = new ArrayList<>();
        int totalPrice = 0;
        for(Reservation tmpReservation : reservationList) {
            serviceItemNameList.add(tmpReservation.getReservationGoods().getServiceItemName());
            totalPrice = totalPrice + tmpReservation.getPaymentAmount();
        }

        return UserReservationDetailResponse.builder()
                .reservationDate(reservation.getReservationDate())
                .serviceStart(reservation.getServiceStart())
                .serviceEnd(reservation.getServiceEnd())
                .serviceItemNameList(serviceItemNameList)
                .reservationNum(reservation.getReservationNum())
                .createdAt(reservation.getCreatedAt())
                .paymentAmount(totalPrice)
                .reservationState(reservation.getReservationState().getValue())
                .address(reservation.getAddress())
                .request(reservation.getRequest())
                .cancelDesc(reservation.getCancelDesc())
                .serviceId(reservation.getServiceId())
                .workerId(reservation.getWorkerId())
                .build();
    }

    // 결제상태 문자열과 일치하는 결제상태Enum값 구하기
    private ReservationState getReservationState(String reservationState) {
        String code = null;
        if(ReservationState.WAIT.getValue().equals(reservationState)) {
            code = "1";
        } else if (ReservationState.CONFIRMED.getValue().equals(reservationState)){
            code = "2";
        } else if (ReservationState.CANCEL.getValue().equals(reservationState)){
            code = "3";
        } else if (ReservationState.REFUSE.getValue().equals(reservationState)){
            code = "4";
        } else if (ReservationState.COMPLETE.getValue().equals(reservationState)){
            code = "5";
        } else {
            // 일치하는 예약상태가 없는 경우 예외처리
            throw new CustomException(ResponseCode.INCORRECT_RESERVATION_STATE);
        }

        return new ReservationStateConverter().convertToEntityAttribute(code);
    }
}