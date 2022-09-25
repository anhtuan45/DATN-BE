package com.ws.masterserver.dto.admin.color;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColorResponseV2 {
    private String id;

    private String name;

    private String hex;

    private Boolean active;

    private Date createdDate;

}