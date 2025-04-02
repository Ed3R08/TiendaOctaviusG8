package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Long>   {
    




//Consulta ampliada para recuperar los productos entre un rango de precio, ordenados por precio
public List<Producto> findByPrecioBetweenOrderByPrecio(
        double precioInf, 
        double precioSup);

//Consulta JPQL para recuperar los productos entre un rango de precio, ordenados por precio
@Query(value="SELECT a FROM Producto a "
        + "WHERE a.precio "
        + "BETWEEN :precioInf AND :precioSup "
        + "ORDER BY a.precio")
public List<Producto> consultaJPQL(
        @Param("precioInf") double precioInf,
        @Param("precioSup") double precioSup);    

//Consulta SQL para recuperar los productos entre un rango de precio, ordenados por precio
@Query(nativeQuery=true,
        value="SELECT * FROM Producto a "
        + "WHERE a.precio "
        + "BETWEEN :precioInf AND :precioSup "
        + "ORDER BY a.precio")
public List<Producto> consultaSQL(
        @Param("precioInf") double precioInf,
        @Param("precioSup") double precioSup); 

}    

  
