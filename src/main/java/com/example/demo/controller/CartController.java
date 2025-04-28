package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Cart cart = cartService.getCart(session);
        List<CartItem> cartItems = cart.getCartItems();
        double totalPrice = calculateTotalPrice(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session){
        cartService.addToCart(session,productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("productId") Long productId,
                                 HttpSession session) {
        cartService.removeFromCart(session, productId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("customerName") String name,
                           @RequestParam("customerPhone") String phone,
                           @RequestParam("customerAddress") String address,
                           HttpSession session){
        Cart cart = cartService.getCart(session);
        List<CartItem> cartItems = cart.getCartItems();
        double totalPrice = calculateTotalPrice(cartItems);

        Order order = new Order();
        order.setName(name);
        order.setPhone(phone);
        order.setAddress(address);
        order.setOrderDate(Date.from(Instant.now()));
        order.setTotalPrice(totalPrice);
        orderService.save(order);

        session.removeAttribute("cart");
        return "redirect:/dashboard";
    }

    private double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0.0;

        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        return totalPrice;
    }
}
