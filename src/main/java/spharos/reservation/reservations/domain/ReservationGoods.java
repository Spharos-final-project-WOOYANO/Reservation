package spharos.reservation.reservations.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reservation_goods")
public class ReservationGoods {

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

}
