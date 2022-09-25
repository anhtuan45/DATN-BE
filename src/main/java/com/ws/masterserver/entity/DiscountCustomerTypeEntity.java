package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "discount_customer_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class DiscountCustomerTypeEntity {
    @Id
    private String id;

    @Column(name = "customer_type_id")
    private String customerTypeId;

    @Column(name = "discount_id")
    private String discountId;
}
