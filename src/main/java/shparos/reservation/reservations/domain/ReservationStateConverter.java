package shparos.reservation.reservations.domain;

import jakarta.persistence.AttributeConverter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class ReservationStateConverter implements AttributeConverter<ReservationState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReservationState attribute) {
        return attribute.getCode();
    }

    @Override
    public ReservationState convertToEntityAttribute(Integer dbData) {
        return EnumSet.allOf(ReservationState.class).stream()
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 코드입니다."));
    }
}
