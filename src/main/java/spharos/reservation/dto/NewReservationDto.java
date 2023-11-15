package spharos.reservation.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class NewReservationDto {

    private Long reservationGoodsId;
    private Long serviceId;
    private Long workerId;
    private String userEmail;
    private LocalDate reservationDate;
    private LocalTime serviceStart;
    private LocalTime serviceEnd;
    private Integer paymentAmount;
    private String request;
    private String address;

}
