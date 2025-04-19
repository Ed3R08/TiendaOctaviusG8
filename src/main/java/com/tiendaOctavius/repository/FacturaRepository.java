package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository <Factura,Long> {
     
}