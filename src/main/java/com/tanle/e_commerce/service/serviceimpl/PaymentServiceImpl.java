package com.tanle.e_commerce.service.serviceimpl;

import com.tanle.e_commerce.Repository.Jpa.OrderJpaRepository;
import com.tanle.e_commerce.Repository.Jpa.PaymentRepository;
import com.tanle.e_commerce.config.payment.VNPayConfig;
import com.tanle.e_commerce.dto.PaymentDTO;
import com.tanle.e_commerce.entities.Order;
import com.tanle.e_commerce.entities.Payment;
import com.tanle.e_commerce.entities.PaymentStatus;
import com.tanle.e_commerce.entities.enums.StatusPayment;
import com.tanle.e_commerce.exception.ResourceNotFoundExeption;
import com.tanle.e_commerce.mapper.PaymentMapper;
import com.tanle.e_commerce.respone.PaymentRespone;
import com.tanle.e_commerce.service.PaymentService;
import com.tanle.e_commerce.utils.payment.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private VNPayConfig vnPayConfig;
    @Autowired
    private OrderJpaRepository orderJpaRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentRespone createPayment(HttpServletRequest request) {
        String bankCode = request.getParameter("bankCode");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Not found order"));

        long amount = order.getOrderDetails()
                .stream()
                .mapToLong(o -> (long) (o.getQuantity() * o.getSku().getPrice()))
                .sum() * 100L;
        Payment payment = Payment.builder()
                .status(StatusPayment.AWAIT)
                .amount(amount/100)
                .createdAt(LocalDateTime.now())
                .order(order)
                .build();
        paymentRepository.save(payment);
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(
                Map.of("paymentId",String.valueOf(payment.getId())));
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return PaymentRespone.builder()
                .code("OK")
//                .paymentDTO(paymentMapper.convertDTO(payment))
                .message("successfully")
                .paymentUrl(paymentUrl)
                .build();

    }

    @Override
    public PaymentDTO handlePayment(HttpServletRequest request) {
        Integer paymentId = Integer.valueOf(request.getParameter("paymentId"));
        String status = request.getParameter("vnp_ResponseCode");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Not found payment"));
        if (status.equals("00")) {
            payment.setStatus(StatusPayment.SUCCESS);
        } else {
            payment.setStatus(StatusPayment.FAILURE);
        }
        paymentRepository.save(payment);
        return paymentMapper.convertDTO(payment);
    }
}