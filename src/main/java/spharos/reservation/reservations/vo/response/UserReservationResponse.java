package spharos.reservation.reservations.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.reservations.dto.UserReservationDto;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserReservationResponse {

    private List<UserReservationDto> reservationDtoList;
    private Integer page;
    private Integer size;
    private Long totalRows;

}
