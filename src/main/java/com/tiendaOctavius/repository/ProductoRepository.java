package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long>   {

//Consulta ampliada para recuperar los productos entre un rango de precio, ordenados por precio
public List<Producto> findByPrecioBetweenOrderByPrecio(
        double precioInf, 
        double precioSup);



}    

  
