package com.tiendaOctavius.controller;

import com.tiendaOctavius.domain.Mantenimiento;
import com.tiendaOctavius.service.MantenimientoService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @GetMapping("/mantenimiento")
    public String mostrarFormulario() {
        return "mantenimiento";
    }

    @PostMapping("/mantenimiento/agendar")
    public String agendarMantenimiento(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String fecha,
            @RequestParam String descripcion,
            Model model) {

        // Crear el objeto Mantenimiento
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setNombre(nombre);
        mantenimiento.setCorreo(correo);
        mantenimiento.setFecha(LocalDateTime.parse(fecha));  // Asegúrate de tener el formato adecuado
        mantenimiento.setDescripcion(descripcion);

        // Llamar al servicio para guardar el mantenimiento
        mantenimientoService.agendarMantenimiento(mantenimiento);

        // Agregar el mensaje a la vista
        model.addAttribute("mensaje", "Tu solicitud ha sido enviada correctamente.");
        return "mantenimiento";
    }
}
