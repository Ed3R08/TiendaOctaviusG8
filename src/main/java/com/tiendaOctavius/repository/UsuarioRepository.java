package com.tiendaOctavius.repository;

import com.tiendaOctavius.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Se usa para el proceso de Log in
    Usuario findByUsername(String username);
    
    //Se usa para buscar un registro de usuario en el proceso de activacion de un nuevo usuario
    Usuario findByUsernameAndPassword(String username, String Password);

    //Se utiliza para validar si dentro de la tabla usuario ya exite un registro con el username o el correo
    Usuario findByUsernameOrCorreo(String username, String correo);

    //Se utiliza para validar si dentro de la tabla usuario ya exite un registro con el username o el correo
    boolean existsByUsernameOrCorreo(String username, String correo);
}
