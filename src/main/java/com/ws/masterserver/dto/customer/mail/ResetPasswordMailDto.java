package com.ws.masterserver.dto.customer.mail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordMailDto {
    private String html;
    private String from;
    private String to;
    private String text;
    private String subject;
}
