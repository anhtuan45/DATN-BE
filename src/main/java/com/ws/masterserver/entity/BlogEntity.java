package com.ws.masterserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogEntity {
    @Id
    private String id;

    private String content;

    private Date createdDate;

    private String title;

    @Column(name = "topic_id")
    private String topicId;

    private String image;

    private String description;

    private String name;

    private Boolean active;




}
