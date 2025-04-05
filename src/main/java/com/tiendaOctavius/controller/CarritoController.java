package com.tiendaOctavius.controller;

import com.tiendaOctavius.domain.Item;
import com.tiendaOctavius.domain.Producto;
import com.tiendaOctavius.service.ItemService;
import com.tiendaOctavius.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductoService productoService;
    
    
    @GetMapping("/agregar/{idProducto}")
    public ModelAndView agregar(Model model, Item item){
        Item item2 = itemService.getItem(item);
        
        //Si el producto aun no esta en la lista se recupera la info de la
        //tabla producto
        if (item2==null){
           //si no esta se busca el producto y se crea item2
           Producto producto = productoService.getProducto(item);
           item2 = new Item(producto);
        }
        //se crea o actualiza el item (producto) en la lista
        itemService.save(item2);
        
        var lista = itemService.getItems();
        var totalCompra = itemService.getTotal();
        model.addAttribute("listaItems",lista);
        model.addAttribute("totalCompra",totalCompra);
        model.addAttribute("totalProductos",lista.size());
        
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }
    
    @GetMapping("/listado")
    public String listado(Model model){
        var lista = itemService.getItems();
        model.addAttribute("listaItems",lista);
        return"/carrito/listado";
    }
    
}