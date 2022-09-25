package com.ws.masterserver.dto.admin.category.detail;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDetailRes {
    private String id;
    private String name;
    private Object des;
    private Object typeId;
    private Object products;
}
