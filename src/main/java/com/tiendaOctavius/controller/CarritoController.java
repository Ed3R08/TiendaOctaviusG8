package com.tiendaOctavius.controller;

import com.tiendaOctavius.domain.Item;
import com.tiendaOctavius.domain.Producto;
import com.tiendaOctavius.service.ItemService;
import com.tiendaOctavius.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final ItemService itemService;
    private final ProductoService productoService;

    // ===============================
    // 1. Agregar un producto al carrito
    // ===============================
    @GetMapping("/agregar/{idProducto}")
    public ModelAndView agregar(Model model, Item item) {
        // ¿El producto ya está en el carrito?
        Item itemExistente = itemService.getItem(item);

        if (itemExistente == null) {
            // Si no estaba, lo creamos a partir de la BD
            Producto p = productoService.getProducto(item);
            itemExistente = new Item(p);
        }
        // Aumentamos o agregamos
        itemService.save(itemExistente);

        // Datos rápidos para el fragmento del header
        var lista = itemService.getItems();
        double totalCompra = itemService.getTotal();

        model.addAttribute("listaItems", lista);
        model.addAttribute("totalCompra", totalCompra);
        model.addAttribute("totalProductos", lista.size());

        // Se devuelve solo el fragmento :: verCarrito
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }

    // Tipo de cambio (colones → dólares)
    @Value("${tcambio:540}")
    private double tCambio;

    // ===============================
    // 2. Mostrar el carrito completo
    // ===============================
    @GetMapping("/listado")
    public String listado(Model model) {

        var lista = itemService.getItems();
        double totalColones = itemService.getTotal();             // total en ₡
        double totalDolares = Math.round((totalColones / tCambio) * 100) / 100.0;

        model.addAttribute("listaItems",     lista);
        model.addAttribute("totalProductos", lista == null ? 0 : lista.size());
        model.addAttribute("carritoTotal",   totalColones); 
        model.addAttribute("totalCompra",    totalColones);
        model.addAttribute("totalDolares",   totalDolares);
        model.addAttribute("precioVenta",    tCambio);             // ¢ por $

        return "/carrito/listado";
    }

    // ===============================
    // 3. Eliminar un producto del carrito
    // ===============================
    @GetMapping("/eliminar/{idProducto}")
    public String eliminar(Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    // ===============================
    // 4. Editar un ítem (mostrar formulario)
    // ===============================
    @GetMapping("/modificar/{idProducto}")
    public String modificar(Model model, Item item) {
        item = itemService.getItem(item);          // recupera del carrito
        model.addAttribute("item", item);
        return "/carrito/modifica";
    }

    // ===============================
    // 5. Guardar cambios de cantidad
    // ===============================
    @PostMapping("/guardar")
    public String guardar(Item item) {
        itemService.update(item);
        return "redirect:/carrito/listado";
    }
}
