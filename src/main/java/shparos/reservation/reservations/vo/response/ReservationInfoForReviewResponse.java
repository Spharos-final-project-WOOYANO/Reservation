package shparos.reservation.reservations.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInfoForReviewResponse {

    private String serviceItemName;
    private LocalDate reservationDate;
    private Long workerId;

}
