package com.ws.masterserver.entity;

import com.ws.masterserver.utils.constants.enums.BodyCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "body_height")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyHeightEntity {
    @Id
    private String id;

    @Column(name = "min_height")
    private Long minHeight;

    @Column(name = "max_height")
    private Long maxHeight;

    @Enumerated(EnumType.STRING)
    private BodyCodeEnum code;
}
