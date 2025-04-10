package com.marien.studi_jo_backend.services.admin.Order;

import com.marien.studi_jo_backend.dto.AnalyticsResponse;
import com.marien.studi_jo_backend.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getAllPlaceOrder();

    OrderDto changeOrderStatus(Long orderId, String status);

    AnalyticsResponse calculateAnalytics();

}
