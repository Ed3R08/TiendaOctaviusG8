package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    // Aquí puedes agregar consultas personalizadas si las necesitas
}
