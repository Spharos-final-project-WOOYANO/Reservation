package spharos.reservation.reservations.domain;

import lombok.Getter;

@Getter
public enum ReservationState {

    WAIT(0,"예약대기"),
    CONFIRMED(1,"예약확정"),
    CANCEL(2,"고객예약취소"),
    REFUSE(3,"업체예약거절"),
    COMPLETE(4,"서비스완료");

    private final Integer code;
    private final String value;

    ReservationState(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

}
