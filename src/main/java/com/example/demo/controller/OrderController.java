package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin-page/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getAllOrder(Model model){
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute("orders", orders);
        return "order_list";
    }
}
