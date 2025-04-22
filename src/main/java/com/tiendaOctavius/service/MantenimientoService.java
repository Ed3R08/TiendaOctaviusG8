package com.tiendaOctavius.service;

import com.tiendaOctavius.domain.Mantenimiento;
import com.tiendaOctavius.repository.MantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    // Método para obtener todos los mantenimientos
    @Transactional(readOnly = true)
    public List<Mantenimiento> getMantenimientos() {
        return mantenimientoRepository.findAll();
    }

    // Método para obtener un mantenimiento específico
    @Transactional(readOnly = true)
    public Mantenimiento getMantenimiento(Long id) {
        Optional<Mantenimiento> mantenimiento = mantenimientoRepository.findById(id);
        return mantenimiento.orElse(null);
    }

    // Método para agendar un mantenimiento
    @Transactional
    public void agendarMantenimiento(Mantenimiento mantenimiento) {
        mantenimientoRepository.save(mantenimiento);
    }

    // Método para eliminar un mantenimiento
    @Transactional
    public void eliminarMantenimiento(Long id) {
        mantenimientoRepository.deleteById(id);
    }

    // Otros métodos según las necesidades...
}
