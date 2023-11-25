package spharos.reservation.reservations.vo.reqeust;

import lombok.Getter;

@Getter
public class ReservationGoodsRegisterRequest {

    private Long serviceId;
    private String serviceItemName;
    private Integer price;
    private Integer serviceTime;
    private String superCategory;
    private String baseCategory;
    private String subCategory;

}
