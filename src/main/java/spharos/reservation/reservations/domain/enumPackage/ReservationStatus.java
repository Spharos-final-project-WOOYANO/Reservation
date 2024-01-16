package spharos.reservation.reservations.domain.enumPackage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import spharos.reservation.global.common.CodeValue;

@Getter
public enum ReservationStatus implements CodeValue {

    PAYMENT_WAITING("0","결제대기"),
    WAIT("1","예약대기"),
    CONFIRMED("2","예약확정"),
    CANCEL("3","고객예약취소"),
    REFUSE("4","업체예약거절"),
    COMPLETE("5","서비스완료"),
    PAYMENT_CANCEL("6","결제실패");

    private final String code;
    private final String value;

    ReservationStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @JsonCreator
    public static ReservationStatus findByValue(String code) {
        for (ReservationStatus type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + code);
    }

    @JsonCreator
    public static ReservationStatus fromValue(String value) {
        for (ReservationStatus type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
