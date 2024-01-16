package spharos.reservation.reservations.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.reservation.global.common.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReservationGoods extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, name = "service_item_name")
    private String serviceItemName;
    @Column(nullable = false, name = "price")
    private int price;

    @Column(nullable = false, length = 20, name = "super_category")
    private String superCategory;
    @Column(nullable = false, length = 20, name = "base_category")
    private String baseCategory;
    @Column(length = 20, name = "sub_category")
    private String subCategory;

    @JoinColumn(name = "service_id")
    private Long serviceId;

    @Builder
    public ReservationGoods(String serviceItemName, int price, String superCategory,
                            String baseCategory, String subCategory, Long serviceId) {
        this.serviceItemName = serviceItemName;
        this.price = price;
        this.superCategory = superCategory;
        this.baseCategory = baseCategory;
        this.subCategory = subCategory;
        this.serviceId = serviceId;
    }

    public static ReservationGoods createReservationGoods(String serviceItemName, int price,
                                          String superCategory, String baseCategory, String subCategory,Long serviceId) {
        return ReservationGoods.builder()
                .serviceItemName(serviceItemName)
                .price(price)
                .superCategory(superCategory)
                .baseCategory(baseCategory)
                .subCategory(subCategory)
                .serviceId(serviceId)
                .build();
    }





}
