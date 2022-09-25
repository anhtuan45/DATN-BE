package com.ws.masterserver.dto.admin.discount.prerequisite;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("qty")
public class QtyPrerequisiteType extends PrerequisiteDto {
    private String minimumQuantity;
}