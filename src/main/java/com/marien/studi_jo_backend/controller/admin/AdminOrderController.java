package com.marien.studi_jo_backend.controller.admin;

import com.marien.studi_jo_backend.dto.AnalyticsResponse;
import com.marien.studi_jo_backend.dto.OrderDto;
import com.marien.studi_jo_backend.services.admin.Order.AdminOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping("/placedOrders")
    @Operation(summary = "Liste de vente")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders(){
        return  ResponseEntity.ok(adminOrderService.getAllPlaceOrder());
    }

    @GetMapping("/order/{orderId}/{status}")
    @Operation(summary = "CHanger le status des ventes")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String status){
        OrderDto orderDto = adminOrderService.changeOrderStatus(orderId, status);

        if(orderDto==null){
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/order/analytics")
    @Operation(summary = "Analyser les ventes")
    public ResponseEntity<AnalyticsResponse> getAnalytics(){
        return  ResponseEntity.ok(adminOrderService.calculateAnalytics());
    }
}
