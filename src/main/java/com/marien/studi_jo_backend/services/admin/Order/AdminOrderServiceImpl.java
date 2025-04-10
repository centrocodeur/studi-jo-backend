package com.marien.studi_jo_backend.services.admin.Order;

import com.marien.studi_jo_backend.dto.AnalyticsResponse;
import com.marien.studi_jo_backend.dto.OrderDto;
import com.marien.studi_jo_backend.entity.Order;
import com.marien.studi_jo_backend.enums.OrderStatus;
import com.marien.studi_jo_backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlaceOrder(){

        List<Order> orderList = orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.DISPONIBLE, OrderStatus.RESERVE, OrderStatus.VENDU));

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            if(Objects.equals(status, "RESERVE")){
                order.setOrderStatus(OrderStatus.RESERVE);
            }else if(Objects.equals(status,"VENDU")){
                order.setOrderStatus(OrderStatus.VENDU);

            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }


    public AnalyticsResponse calculateAnalytics(){

        LocalDate currentDate= LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        Long currentMonthOrders =  getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthOrders= getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear() );

        Long currentMonthEarnings= getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarnings= getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());


        Long placed = orderRepository.countByOrderStatus(OrderStatus.DISPONIBLE);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.RESERVE);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.VENDU);

        return new AnalyticsResponse(placed, shipped, delivered, currentMonthOrders, previousMonthOrders,
                currentMonthEarnings,previousMonthEarnings);
    }



    public Long getTotalOrdersForMonth(int month, int year){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth= calendar.getTime();

        //Move the calendar to the end of the specified month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.VENDU);

        return  (long)orders.size();

    }


    private Long getTotalEarningsForMonth(int month, int year) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth= calendar.getTime();

        //Move the calendar to the end of the specified month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.VENDU);

        Long sum = 0L;

        for (Order order: orders){
            sum += order.getAmount();
        }

        return sum;
    }




}
