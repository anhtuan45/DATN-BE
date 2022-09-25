package com.ws.masterserver.dto.admin.size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeResponseV2 {
    private String id;

    private String name;

    private String code;

    private Boolean active;

    private Date createdDate;

}