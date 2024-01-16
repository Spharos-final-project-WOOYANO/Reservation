package spharos.reservation.reservations.domain.enumPackage;

import jakarta.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

public class ReservationStateConverter implements AttributeConverter<ReservationStatus, String> {
    @Override
    public String convertToDatabaseColumn(ReservationStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ReservationStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ReservationStatus.class).stream()
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 코드입니다."));
    }
}
