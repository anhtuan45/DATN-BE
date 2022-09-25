package com.ws.masterserver.dto.dob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountNotificationDto {
    private String discountId;
    private String email;

}
