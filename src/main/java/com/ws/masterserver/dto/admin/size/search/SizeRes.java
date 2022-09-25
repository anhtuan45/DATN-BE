package com.ws.masterserver.dto.admin.size.search;

import com.ws.masterserver.utils.base.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SizeRes {
    private String id;
    private String name;
    private StatusDto status;
}
