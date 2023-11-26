package spharos.reservation.reservations.domain;

import lombok.Getter;
import spharos.reservation.global.common.CodeValue;

@Getter
public enum ReservationState implements CodeValue {

    PAYMENT_WAITING("0","결제대기"),
    WAIT("1","예약대기"),
    CONFIRMED("2","예약확정"),
    CANCEL("3","고객예약취소"),
    REFUSE("4","업체예약거절"),
    COMPLETE("5","서비스완료"),
    PAYMENT_CANCEL("6","결제실패");

    private final String code;
    private final String value;

    ReservationState(String code, String value) {
        this.code = code;
        this.value = value;
    }

}