package com.retailease.billingsystem.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.retailease.billingsystem.io.OrderResponse;
import com.retailease.billingsystem.io.PaymentRequest;
import com.retailease.billingsystem.io.PaymentVerificationRequest;
import com.retailease.billingsystem.io.RazorpayOrderResponse;
import com.retailease.billingsystem.service.OrderService;
import com.retailease.billingsystem.service.RazorpayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final RazorpayService razorpayService;
	private final OrderService orderService;

	@PostMapping("/create-order")
	@ResponseStatus(HttpStatus.CREATED)
	public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException {
		return razorpayService.createOrder(request.getAmount(), request.getCurrency());
	}
	
	@PostMapping("/verify")
	public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request) {
	return orderService. verifyPayment(request);
	}
	
	
	
}