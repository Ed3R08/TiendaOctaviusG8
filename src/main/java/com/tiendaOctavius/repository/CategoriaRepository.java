
package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>   {
    
}
