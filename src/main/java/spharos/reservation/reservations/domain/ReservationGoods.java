package spharos.reservation.reservations.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.global.common.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reservation_goods")
public class ReservationGoods extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "service_id")
    private Long serviceId;
    @Column(nullable = false, length = 30, name = "service_item_name")
    private String serviceItemName;
    @Column(nullable = false, name = "price")
    private Integer price;
    @Column(nullable = false, name = "service_time")
    private Integer serviceTime;
    @Column(nullable = false, length = 20, name = "super_category")
    private String superCategory;
    @Column(nullable = false, length = 20, name = "base_category")
    private String baseCategory;
    @Column(length = 20, name = "sub_category")
    private String subCategory;

    private ReservationGoods(Long serviceId,String serviceItemName, Integer price, Integer serviceTime,
                             String superCategory, String baseCategory, String subCategory) {
        this.serviceId = serviceId;
        this.serviceItemName = serviceItemName;
        this.price = price;
        this.serviceTime = serviceTime;
        this.superCategory = superCategory;
        this.baseCategory = baseCategory;
        this.subCategory = subCategory;
    }

    // 상품 등록
    public static ReservationGoods createReservationGoods(Long serviceId, String serviceItemName, Integer price,
                                                          Integer serviceTime, String superCategory,
                                                          String baseCategory, String subCategory){
        return new ReservationGoods(serviceId, serviceItemName, price, serviceTime, superCategory,
                baseCategory, subCategory);
    }

}
