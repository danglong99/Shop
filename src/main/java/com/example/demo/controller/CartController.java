package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
public class CartController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"/products"})
    public String viewProduct(Model model){
        model.addAttribute("listProduct" ,productService.getAll());
        return "products";
    }
    @RequestMapping(value = "/buyProduct/{id}", method = RequestMethod.GET)
    public String viewAdd(ModelMap mm, HttpSession session, @PathVariable("id") String productId) {
        HashMap<String, Cart> cartItems = (HashMap<String, Cart>) session.getAttribute("myCartItems");
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
        Product product = productService.getById(productId);
        if (product != null) {
            if (cartItems.containsKey(productId)) {
                Cart item = cartItems.get(productId);
                item.setProduct(product);
                item.setQuantity(item.getQuantity() + 1);
                cartItems.put(productId, item);
            } else {
                Cart item = new Cart();
                item.setProduct(product);
                item.setQuantity(1);
                cartItems.put(productId, item);
            }
        }
        session.setAttribute("myCartItems", cartItems);
        session.setAttribute("myCartTotal", totalPrice(cartItems));
        session.setAttribute("myCartNum", cartItems.size());
        return "cart";
    }

    @RequestMapping(value = "/sub/{id}", method = RequestMethod.GET)
    public String viewUpdate(ModelMap mm, HttpSession session, @PathVariable("id") long productId) {
        HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
        session.setAttribute("myCartItems", cartItems);
        return "cart";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String viewRemove(ModelMap mm, HttpSession session, @PathVariable("id") long productId) {
        HashMap<String, Cart> cartItems = (HashMap<String, Cart>) session.getAttribute("myCartItems");
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
        if (cartItems.containsKey(productId)) {
            cartItems.remove(productId);
        }
        session.setAttribute("myCartItems", cartItems);
        session.setAttribute("myCartTotal", totalPrice(cartItems));
        session.setAttribute("myCartNum", cartItems.size());
        return "cart";
    }

    public double totalPrice(HashMap<String, Cart> cartItems) {
        int count = 0;
        for (Map.Entry<String, Cart> list : cartItems.entrySet()) {
            count += list.getValue().getProduct().getPrice() * list.getValue().getQuantity();
        }
        return count;
    }

}
