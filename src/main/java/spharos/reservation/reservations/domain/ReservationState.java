package spharos.reservation.reservations.domain;

import lombok.Getter;

@Getter
public enum ReservationState {

    PAYMENT_WAITING(0,"결제대기"),
    WAIT(1,"예약대기"),
    CONFIRMED(2,"예약확정"),
    CANCEL(3,"고객예약취소"),
    REFUSE(4,"업체예약거절"),
    COMPLETE(5,"서비스완료");

    private final Integer code;
    private final String value;

    ReservationState(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

}
