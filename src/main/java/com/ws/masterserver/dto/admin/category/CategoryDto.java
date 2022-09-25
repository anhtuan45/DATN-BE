package com.ws.masterserver.dto.admin.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private String id;
    private String name;
    private String des;
    private String typeId;
    private List<String> productIds;
}
