package in.bushansirgur.billingsoftware.service;

import java.time.LocalDate;
import java.util.List;

import in.bushansirgur.billingsoftware.io.OrderRequest;
import in.bushansirgur.billingsoftware.io.OrderResponse;
import in.bushansirgur.billingsoftware.io.PaymentVerificationRequest;

public interface OrderService {

	OrderResponse createOrder(OrderRequest request);

	void deleteOrder(String orderId);

	List<OrderResponse> getLatestOrders();
	
	OrderResponse verifyPayment(PaymentVerificationRequest request);
	
	Double sumSalesByDate(LocalDate date);

	Long countByOrderDate(LocalDate date);

	List<OrderResponse> findRecentOrders();
	
}