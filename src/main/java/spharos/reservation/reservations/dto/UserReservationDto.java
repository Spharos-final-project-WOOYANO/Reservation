package spharos.reservation.reservations.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReservationDto {

    private String reservationNum;
    private String serviceType;
    private String reservationState;
    private List<String> serviceItemNameList;
    private LocalDate reservationDate;
    private LocalTime serviceStart;
    private LocalTime serviceEnd;
    private Long serviceId;
    private Long workerId;


}
