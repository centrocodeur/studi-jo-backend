package com.marien.studi_jo_backend;

import com.marien.studi_jo_backend.entity.Order;
import com.marien.studi_jo_backend.entity.User;
import com.marien.studi_jo_backend.enums.OrderStatus;
import com.marien.studi_jo_backend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class OrderRepositoryTest {


    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp(){
        // Initialisation des mocks

        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void orderRepositoryTest(){

        // Arrange
        User user = new User();
        Order order= new Order();
        user.setActivated(true);
        user.setFirstname("Tapande");
        user.setLastname("Marien");
        user.setEmail("marientaps@gmail.com");
        user.setPassword("123456");

        order.setOrdersDescription("Billet");
        order.setDate(new Date());
        order.setOrderStatus(OrderStatus.Delivered);
        order.setAmount(122l);
        order.setTotalAmount(122L);
        order.setUser(user);
        order.setPayment("CARD");
        order.setTrackingId(UUID.randomUUID());

        Order order2= new Order();
        order2.setAmount(0l);
        order2.setTotalAmount(0l);
        order2.setDiscount(0l);
        order2.setUser(user);
        order2.setOrderStatus(OrderStatus.Pending);


        when(orderRepository.findByUserIdAndOrderStatus(1L,OrderStatus.Delivered)).thenReturn(order);
        when(orderRepository.findByUserIdAndOrderStatus(1L,OrderStatus.Pending)).thenReturn(order2);

        when(orderRepository.countByOrderStatus(OrderStatus.Delivered)).thenReturn(1L);
        List<Order> orderList =orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.Pending));
        List<Order> orderList2 =orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.placed));

        List<Order> orders= orderRepository.findByUserIdAndOrderStatusIn(1L, List.of(OrderStatus.Delivered));

        Optional<Order> orders1= orderRepository.findByTrackingId(order.getTrackingId());


        assertThat(orderList).isNotNull();
        //assertThat(orderList2).isNull();
        assertThat(orders).isNotNull();
        assertThat(orders1).isNotNull();




    }




}
