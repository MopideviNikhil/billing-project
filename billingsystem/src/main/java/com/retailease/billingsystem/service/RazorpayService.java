package com.retailease.billingsystem.service;

import com.razorpay.RazorpayException;
import com.retailease.billingsystem.io.RazorpayOrderResponse;

public interface RazorpayService {
	RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}