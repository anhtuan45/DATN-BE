package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "suggest")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestEntity {
    @Id
    private String id;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "size_id")
    private String sizeId;

    @Column(name = "body_weight_id")
    private String bodyWeightId;

    @Column(name = "body_height_id")
    private String bodyHeightId;
}
