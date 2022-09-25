package com.ws.masterserver.controller;


import com.ws.masterserver.config.vnpay.VnPayConfig;
import com.ws.masterserver.dto.customer.order.pay.PayRequest;
import com.ws.masterserver.entity.OrderEntity;
import com.ws.masterserver.entity.TransactionHistoryEntity;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.UidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/vnpay")
@RequiredArgsConstructor
public class VNPayController extends WsController {

    private final WsRepository repository;

    @PostMapping("/checkout")
    public ResponseEntity<?> payment(
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "bankCode") String bankCode
    ) throws IOException {
        String vnp_Version = "2.0.1";
        String vnp_Command = "pay";
        String vnp_OrderInfo = description;
        String orderType = "170000";
        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "192.168.1.1";
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;//Mã website


        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = bankCode;
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_Locale", "vn");
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(date);

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;

        ResData resdata = new ResData(paymentUrl,"00","success");

        return ResponseEntity.ok(resdata);
    }


    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody PayRequest request) throws Exception {
        String vnp_Version = "2.0.1";
        String vnp_Command = "pay";
        String vnp_OrderInfo = request.getDescription();
        String orderType = "170000";
        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "192.168.1.1";
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;//Mã website

        Map<String, String> vnp_Params = new HashMap<>();

        Long amount = request.getAmount() * 100;

        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = request.getBankCode();
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_Locale", "vn");
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(date);

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;

        ResData resdata = new ResData(paymentUrl,"00","success");

        return ResponseEntity.ok(resdata);

    }

    //Thong tin giao dich
    @GetMapping("/thong-tin-thanh-toan")
    public ResponseEntity<?> thongTinThanhToan(
            @RequestParam(value = "vnp_Amount", required = false) String amout,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_BankTranNo", required = false) String bankTranNo,
            @RequestParam(value = "vnp_CardType", required = false) String cartType,
            @RequestParam(value = "vnp_OrderInfo", required = false) String orDerInfo,
            @RequestParam(value = "vnp_PayDate", required = false) String payDate,
            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode,
            @RequestParam(value = "vnp_TmnCode", required = false) String tmnCode,
            @RequestParam(value = "vnp_TransactionNo", required = false) String transactionNo,
            @RequestParam(value = "vnp_TransactionStatus", required = false) String transactionStatus,
            @RequestParam(value = "vnp_TxnRef", required = false) String txnRef,
            @RequestParam(value = "vnp_SecureHash", required = false) String secureHash
    ) {
        try {

            ResData resData = new ResData();

            if(responseCode.equalsIgnoreCase("24")){
                resData.setData(null);
                resData.setStatus("BAD_REQUEST");
                resData.setMessage("Giao dịch thất bại !");
                return ResponseEntity.badRequest().body(resData);
            }

            Optional<TransactionHistoryEntity> optionalTrans = repository.transactionHistoryRepository.findByTransactionNo(transactionNo);

            TransactionHistoryEntity transactionHistory = null;

            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(payDate);

            if (optionalTrans.isPresent()) {
                transactionHistory = optionalTrans.get();
            } else {
                transactionHistory = TransactionHistoryEntity.builder()
                        .id(UidUtils.generateUid())
                        .transactionNo(transactionNo)
                        .bankCode(bankCode)
                        .invoiceNumber(txnRef)
                        .status(transactionStatus)
                        .orderInfo(orDerInfo)
                        .createDate(DateUtils.toStr(date,DateUtils.DATE_TIME_FORMAT_VI_OUTPUT))
                        .amount(Integer.valueOf(amout) / 100)
                        .orderId(orDerInfo)
                        .build();

                repository.transactionHistoryRepository.save(transactionHistory);

                OrderEntity order = repository.orderRepository.findById(orDerInfo).orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn! ");
                });

                order.setPayed(Boolean.TRUE);
                repository.orderRepository.save(order);

            }

            resData.setData(transactionHistory);
            resData.setStatus("OK");
            resData.setMessage("Giao dịch thành công !");

            return ResponseEntity.ok().body(resData);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("FAILED");
        }

    }
}
