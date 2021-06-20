package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.implement.ProductServiceImpl;
import com.example.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Cart.class) {

        }

    }
    @RequestMapping(value = {"","/products"})
    public String viewProduct(Model model){
        model.addAttribute("listProduct" ,productService.getAll());
        return "products";
    }
    @RequestMapping({"/buyProduct"})
    public String listProductHandler(HttpServletRequest request, Model model, //
                                     @RequestParam(value = "id", defaultValue = "") String id) {

        Product product = null;
        if (id != null && id.length() > 0) {
            product = productService.getById(id);
        }
        if (product != null) {

            Cart cart = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cart.addProduct(productInfo, 1);
        }

        return "redirect:/cart";
    }

    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model, //
                                       @RequestParam(value = "id", defaultValue = "") String id) {
        Product product = null;
        if (id != null && id.length() > 0) {
            product = productService.getById(id);
        }
        if (product != null) {

            Cart cart = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cart.removeProduct(productInfo);

        }

        return "redirect:/cart";
    }

    // POST: Cập nhập số lượng cho các sản phẩm đã mua.
    @RequestMapping(value = { "/cart" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, //
                                        Model model, //
                                        @ModelAttribute("cartForm") Cart cartForm) {

        Cart cart = Utils.getCartInSession(request);
        cart.updateQuantity(cartForm);

        return "redirect:/cart";
    }

    // GET: Hiển thị giỏ hàng.
    @RequestMapping(value = { "/cart" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        Cart myCart = Utils.getCartInSession(request);

        model.addAttribute("cartForm", myCart);
        return "cart";
    }
}
