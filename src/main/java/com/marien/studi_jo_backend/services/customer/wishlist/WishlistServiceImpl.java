package com.marien.studi_jo_backend.services.customer.wishlist;

import com.marien.studi_jo_backend.dto.WishlistDto;
import com.marien.studi_jo_backend.entity.Ticket;
import com.marien.studi_jo_backend.entity.User;
import com.marien.studi_jo_backend.entity.Wishlist;
import com.marien.studi_jo_backend.repository.TicketRepository;
import com.marien.studi_jo_backend.repository.UserRepository;
import com.marien.studi_jo_backend.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;

    private final WishlistRepository wishlistRepository;

    @Override
    public WishlistDto addTicketToWishlist(WishlistDto wishlistDto) {
        Optional <Ticket> optionalTicket = ticketRepository.findById(wishlistDto.getTicketId());
        Optional <User> optionalUser = userRepository.findById(wishlistDto.getUserId());
        if(optionalTicket.isPresent() && optionalUser.isPresent()){
            Wishlist wishlist = new Wishlist();
            wishlist.setTicket(optionalTicket.get());
            wishlist.setUser(optionalUser.get());

            return wishlistRepository.save(wishlist).getWishlistDto();
        }

        return null;
    }

    @Override
    public List<WishlistDto> getWishlistByUserId(Long userId) {
        return wishlistRepository.findAllByUserId(userId).stream().map(Wishlist::getWishlistDto).collect(Collectors.toList());
    }
}
