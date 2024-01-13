package spharos.reservation.reservations.axon.event;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.reservations.domain.enumPackage.PaymentMethod;
import spharos.reservation.reservations.domain.enumPackage.PaymentStatus;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentSaveEvent {

    private String reservation_num;

    private String clientEmail;
    private PaymentMethod paymentType;
    private int totalAmount;
    private LocalDateTime approvedAt;
    private PaymentStatus paymentStatus;
}
