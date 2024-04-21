package com.market.marketnexus.service;

import com.market.marketnexus.helpers.sale.Utils;
import com.market.marketnexus.model.CartLineItem;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.CartLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class CartLineItemService {
   @Autowired
   protected CartLineItemRepository cartLineItemRepository;

   public CartLineItem getCartLineItem(Long cartLineItemId) {
      return this.cartLineItemRepository.findById(cartLineItemId).orElse(null);
   }

   public Set<CartLineItem> getAllCartLineItemsByUser(User user) {
      return this.cartLineItemRepository.findAllByUser(user);
   }

   @Transactional
   public CartLineItem saveCartLineItem(CartLineItem cartLineItem) {
      return this.cartLineItemRepository.save(cartLineItem);
   }

   @Transactional
   public Boolean deleteCartLineItem(CartLineItem cartLineItemToDelete) {
      this.cartLineItemRepository.delete(cartLineItemToDelete);
      // TODO: RETURN
      return true;
   }

   @Transactional
   public CartLineItem deleteCartLineItemById(Long cartLineItemId) {
      Optional<CartLineItem> cartToDelete = this.cartLineItemRepository.findById(cartLineItemId);
      this.cartLineItemRepository.deleteById(cartLineItemId);
      return cartToDelete.orElse(null);
   }

   public Float getTotalCartPriceByUser(User user) { // TODO: In Cart Model
      Float total = 0.0F;
      Set<CartLineItem> carts = this.getAllCartLineItemsByUser(user);
      for (CartLineItem cart : carts) {
         total += cart.getSale().getSalePrice();
      }
      return Utils.roundNumberTo2Decimals(total);
   }
}