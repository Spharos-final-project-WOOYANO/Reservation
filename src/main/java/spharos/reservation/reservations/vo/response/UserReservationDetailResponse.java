package spharos.reservation.reservations.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserReservationDetailResponse {

    private LocalDate reservationDate;
    private LocalTime serviceStart;
    private LocalTime serviceEnd;
    private List<String> serviceItemNameList;
    private String reservationNum;
    private LocalDateTime createdAt;
    private Integer paymentAmount;
    private String reservationState;
    private String address;
    private String request;
    private String cancelDesc;
    private Long serviceId;
    private Long workerId;

}
