package com.tiendaOctavius.service;

import com.tiendaOctavius.domain.Producto;
import com.tiendaOctavius.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        var lista = productoRepository.findAll();
        if(activos){
            lista.removeIf(e -> !e.isActivo() );
        }
        
        return lista;

    }

    //Se escriben los metodos de un CRUD(Create, Read, Update, Delete)
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {

        return productoRepository.findById(producto.getIdProducto())
                .orElse(null);
    }

    @Transactional
    public void delete(Producto producto) {
        //Elimina el registro que contiene el ID a lo pasado en producto,getIdProducto()
        productoRepository.delete(producto);
    }

    @Transactional
    public void save(Producto producto) {
        // Si producto.idProducto esta vacio... se inserta un registro
        // Si producto.idProducto tiene algo... se modifica el registro
        productoRepository.save(producto);
    }



    
    @Transactional(readOnly = true)
    public List<Producto> consultaAmpliada(double precioInf, double precioSup) {
        return productoRepository.findByPrecioBetweenOrderByPrecio(precioInf, precioSup);

    }

    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(double precioInf, double precioSup) {
        return productoRepository.consultaJPQL(precioInf, precioSup);

    }

    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(double precioInf, double precioSup) {
        return productoRepository.consultaSQL(precioInf, precioSup);

    }
}
