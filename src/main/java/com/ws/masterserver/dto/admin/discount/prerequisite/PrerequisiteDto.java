package com.ws.masterserver.dto.admin.discount.prerequisite;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@Data
@JsonSubTypes({
        @JsonSubTypes.Type(value = TotalPrerequisiteType.class, name = "total"),
        @JsonSubTypes.Type(value = QtyPrerequisiteType.class, name = "qty"),
        @JsonSubTypes.Type(value = NonePrerequisiteType.class, name = "none"),
})
public class PrerequisiteDto {
}