package spharos.reservation.reservations.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationGoodsListResponse {

    private Long goodsId;
    private String serviceItemName;
    private LocalDateTime createdAt;
    private Integer serviceTime;
    private Integer price;

}
