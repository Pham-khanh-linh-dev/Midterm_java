package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private ProductService productService;
    public void addToCart(HttpSession session, Long productId, int quantity) {
        // Get the user's cart from the session
        Cart cart = (Cart) session.getAttribute("cart");

        // If the cart doesn't exist in the session, create a new one
        if (cart == null) {
            cart = new Cart();
        }

        // Get product details from ProductService
        Product product = productService.getById(productId);

        // Create a new CartItem and add it to the cart
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cart.getCartItems().add(cartItem);

        // Update the cart in the session
        session.setAttribute("cart", cart);
    }

    public Cart getCart(HttpSession session) {
        // Get the user's cart from the session
        Cart cart = (Cart) session.getAttribute("cart");

        // If the cart doesn't exist in the session, create a new one
        if (cart == null) {
            cart = new Cart();
        }

        return cart;
    }

    public void removeFromCart(HttpSession session, Long productId){
        Cart cart = (Cart) session.getAttribute("cart");
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
        session.setAttribute("cart", cart);
    }
}
