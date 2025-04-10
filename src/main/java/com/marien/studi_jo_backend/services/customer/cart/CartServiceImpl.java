package com.marien.studi_jo_backend.services.customer.cart;

import com.marien.studi_jo_backend.dto.AddTicketInCartDto;
import com.marien.studi_jo_backend.dto.CartItemsDto;
import com.marien.studi_jo_backend.dto.OrderDto;
import com.marien.studi_jo_backend.dto.PlaceOrderDto;
import com.marien.studi_jo_backend.entity.*;
import com.marien.studi_jo_backend.enums.OrderStatus;
import com.marien.studi_jo_backend.enums.TicketStatus;
import com.marien.studi_jo_backend.exceptions.ValidationException;
import com.marien.studi_jo_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {



    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CouponRepository couponRepository;

    public ResponseEntity<?> addTicketToCart(AddTicketInCartDto addTicketInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addTicketInCartDto.getUserId(), OrderStatus.RESERVE);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByTicketIdAndOrderIdAndUserId(addTicketInCartDto.getTicketId(),
                activeOrder.getId(), addTicketInCartDto.getUserId());

        if(optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else {
            Optional<Ticket> optionalTicket= ticketRepository.findById(addTicketInCartDto.getTicketId());
            Optional<User> optionalUser= userRepository.findById(addTicketInCartDto.getUserId());

            if( optionalTicket.isPresent() && optionalUser.isPresent()){
                CartItems cart = new CartItems();
                cart.setTicket( optionalTicket.get());
                cart.setPrice( optionalTicket.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updateCart = cartItemsRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount()+ cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount()+ cart.getPrice());

                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }
    }

    public OrderDto getCartByUSerId(Long userId){
        Order activeOrder  = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.RESERVE);
        List<CartItemsDto> cartItemsDtoList =activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());

        OrderDto orderDto= new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);

        if (activeOrder.getCoupon()!=null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }


    public OrderDto applyCoupon(Long userId, String code){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.RESERVE);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found."));

        if (couponIsExpired(coupon)){
            throw new ValidationException("Coupon has expired.");
        }

        double discountAmount = ((coupon.getDiscount()/100.0)* activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount()-discountAmount;

        activeOrder.setAmount((long)netAmount);
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon){

        Date currentDate = new Date();
        Date expirationDate= coupon.getExpirationDate();

        return expirationDate !=null && currentDate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity(AddTicketInCartDto addTicketInCartDto){
        // Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addTicketInCartDto.getUserId(), OrderStatus.RESERVE);
        Optional<Ticket> optionalTicket= ticketRepository.findById(addTicketInCartDto.getTicketId());

        Optional<CartItems> optionalCartItem= cartItemsRepository.findByTicketIdAndOrderIdAndUserId(
                addTicketInCartDto.getTicketId(), activeOrder.getId(), addTicketInCartDto.getUserId()
        );

        if (optionalTicket.isPresent() && optionalCartItem.isPresent()){
            CartItems cartItem =optionalCartItem.get();
            Ticket ticket= optionalTicket.get();

            activeOrder.setAmount(activeOrder.getAmount()+ ticket.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount()+ ticket.getPrice());

            cartItem.setQuantity(cartItem.getQuantity()+1);

            if (activeOrder.getCoupon()!=null){
                double discountAmount = ((activeOrder.getCoupon().getDiscount()/100.0)* activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount()-discountAmount;
                /*
                activeOrder.setAmount((long)netAmount);
                activeOrder.setDiscount((long)discountAmount);

                 */
                activeOrder.setAmount((long)netAmount);
                activeOrder.setDiscount((long)discountAmount);

            }
            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto decreaseProductQuantity(AddTicketInCartDto addTicketInCartDto){
        //TicketOrder activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addTicketInCartDto.getUserId(), OrderStatus.RESERVE);
        Optional<Ticket> optionalTicket= ticketRepository.findById(addTicketInCartDto.getTicketId());

        Optional<CartItems> optionalCartItem= cartItemsRepository.findByTicketIdAndOrderIdAndUserId(
                addTicketInCartDto.getTicketId(), activeOrder.getId(), addTicketInCartDto.getUserId()
        );

        if (optionalTicket.isPresent() && optionalCartItem.isPresent()){
            CartItems cartItem =optionalCartItem.get();
            Ticket ticket= optionalTicket.get();

            activeOrder.setAmount(activeOrder.getAmount() - ticket.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - ticket.getPrice());

            cartItem.setQuantity(cartItem.getQuantity()-1);

            if (activeOrder.getCoupon()!=null){
                double discountAmount = ((activeOrder.getCoupon().getDiscount()/100.0)* activeOrder.getTotalAmount());
                double netAmount = activeOrder.getTotalAmount()-discountAmount;

                activeOrder.setAmount((long)netAmount);
                activeOrder.setDiscount((long)discountAmount);

            }
            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.RESERVE);

        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
        if (optionalUser.isPresent()){
            activeOrder.setOrdersDescription(placeOrderDto.getOrderDescription());
            activeOrder.setEmail(placeOrderDto.getEmail());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.VENDU);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

            // Create a new cart for the user
            Order order= new Order();
            order.setAmount(0l);
            order.setTotalAmount(0l);
            order.setDiscount(0l);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.RESERVE);
            orderRepository.save(order);

            return activeOrder.getOrderDto();
        }

        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long userId){

        return orderRepository.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.RESERVE, OrderStatus.VENDU))
                .stream().map(Order::getOrderDto).collect(Collectors.toList());

    }

    public OrderDto searchOrderByTrackingId(UUID trackingId){
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
        if (optionalOrder.isPresent()){
            return optionalOrder.get().getOrderDto();
        }

        return null;
    }

}
