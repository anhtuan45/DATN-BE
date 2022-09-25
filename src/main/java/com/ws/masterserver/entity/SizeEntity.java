package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "size")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeEntity {
    @Id
    private String id;
    private String name;
    private Boolean active;

    @Column(name = "created_date")
    private Date createdDate;

}
