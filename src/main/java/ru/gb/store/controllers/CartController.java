package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.store.entities.Order;
import ru.gb.store.entities.OrderStatus;
import ru.gb.store.service.CartService;
import ru.gb.store.service.UserService;

import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final UserService userService;
    private final CartService cartService;

    @ModelAttribute
    public void attributes(Model model) {
        model.addAttribute("cart", cartService.getUserSessionCart());
        model.addAttribute("prodInCart", cartService.getUserSessionCart().getProductCart().keySet());
        model.addAttribute("prodAndCount", cartService.getUserSessionCart().getProductCart());
        model.addAttribute("totalSum", cartService.getUserSessionCart().getTotalSum());
        model.addAttribute("discount", cartService.getUserSessionCart().getDiscount());
        model.addAttribute("countOfProducts", cartService.getUserSessionCart().getProductsCount());
    }

    @GetMapping
    public String cart(Principal principal) {
        if (principal != null) {
            userService.saveUserCartWithProducts(userService.findUserByEmail(principal.getName()).getCart());
        }
        return "cart";
    }

    @GetMapping("/add")
    public String addProductToCart(@RequestParam(name = "id") Long prodId) {
        cartService.addToCart(prodId);
        return "redirect:/cart";
    }

    @GetMapping("/delete")
    public String totalRemoveFromCart(@RequestParam(name = "id") Long prodId) {
        cartService.deleteProduct(prodId);
        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String removeOne(@RequestParam(name = "id") Long prodId) {
        cartService.removeOne(prodId);
        return "redirect:/cart";
    }


    @GetMapping("/order")
    public String buyProducts(Model model) {
        model.addAttribute("order", new Order());
        return "order";
    }

    @PostMapping("/order/checkout")
    public String checkout(Principal principal, Order order) {
        order.setUser(userService.findUserByEmail(principal.getName()));
        order.setStatus(OrderStatus.NEW);
        cartService.buyProducts(order);
        return "order";

    }

}
