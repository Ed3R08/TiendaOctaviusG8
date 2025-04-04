
package com.tiendaOctavius.controller;

import com.tiendaOctavius.domain.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    
    @GetMapping("/agregar/{idProducto}")
    public ModelAndView agregar(Model model, Item item){
        
        
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }
}