package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.mail.OrderInfoMail;
import com.ws.masterserver.dto.customer.mail.ResetPasswordMailDto;
import com.ws.masterserver.dto.dob.DiscountNotificationDto;

import javax.mail.MessagingException;
import java.util.concurrent.CompletableFuture;

/**
 * @author myname
 */
public interface MailService {
    CompletableFuture<Void> sendWForgotPassword(ResetPasswordMailDto rsmd) throws MessagingException;

    CompletableFuture<Void> send4OrderInfo(OrderInfoMail orderInfo) throws MessagingException;

    void sendDiscountNotification(DiscountNotificationDto dto);
}
