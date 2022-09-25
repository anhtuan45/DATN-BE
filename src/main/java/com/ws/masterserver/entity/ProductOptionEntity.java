package com.ws.masterserver.entity;

import com.ws.masterserver.utils.constants.enums.SizeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_option")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProductOptionEntity {
    @Id
    private String id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "color_id")
    private String colorId;

    private Long qty;

    private Long price;

    private String image;

    private Boolean active;

    @Column(name = "size_id")
    private String sizeId;
}
