package com.retailease.billingsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.retailease.billingsystem.io.OrderRequest;
import com.retailease.billingsystem.io.OrderResponse;
import com.retailease.billingsystem.io.PaymentVerificationRequest;

public interface OrderService {

	OrderResponse createOrder(OrderRequest request);

	void deleteOrder(String orderId);

	List<OrderResponse> getLatestOrders();
	
	OrderResponse verifyPayment(PaymentVerificationRequest request);
	
	Double sumSalesByDate(LocalDate date);

	Long countByOrderDate(LocalDate date);

	List<OrderResponse> findRecentOrders();
	
}