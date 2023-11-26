package spharos.reservation.reservations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationWorkerDto {
    private Long workerId;
    private Long serviceId;
    private String reservationNum;
}
