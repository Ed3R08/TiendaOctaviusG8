package com.tiendaOctavius.service;

import com.tiendaOctavius.domain.Factura;
import com.tiendaOctavius.domain.Item;
import com.tiendaOctavius.domain.Usuario;
import com.tiendaOctavius.domain.Venta;
import com.tiendaOctavius.repository.FacturaRepository;
import com.tiendaOctavius.repository.ProductoRepository;
import com.tiendaOctavius.repository.UsuarioRepository;
import com.tiendaOctavius.repository.VentaRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    //Se usa una variable de sesion para guardar el carrito de compras 
    @Autowired
    private HttpSession session;

    //El siguuiente metodo crea un item en la variable de sesion
    //si la variable no existe se crea
    public void save(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la variable ya existe en la sesion
        if (lista == null) {
            //Si no estaba se crea...
            lista = new ArrayList<Item>();
        }
        //Se busca en la lista si el producto ya existe ...
        boolean existe = false;
        for (Item i : lista) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                existe = true;
                if (i.getCantidad() < i.getExistencias()) {
                    //Aun se puede comprar
                    i.setCantidad(i.getCantidad() + 1);
                }
                break;
            }
        }
        if (!existe) {//Si no esta el producto en la lista se agrega
            item.setCantidad(1);
            lista.add(item);
        }
        session.setAttribute("listaItems", lista);
    }

    //El siguuiente metodo recupera un item de la lista
    //si no esta retorna null 
    public Item getItem(Item item) {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la variable ya existe en la sesion
        if (lista == null) {
            return null;
        }
        //Se busca en la lista el producto  ...
        for (Item i : lista) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                return i;
            }
        }

        return null;
    }

    //El siguuiente metodo da el total de compra actual del carrito
    //si no hay da 0 
    public double getTotal() {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la variable ya existe en la sesion
        if (lista == null) {
            return 0;
        }
        //Se recorre la lista de productos ...
        double total = 0;
        for (Item i : lista) {
            total += i.getCantidad() * i.getPrecio();
        }

        return total;
    }

    //El siguuiente metodo retorna la lista completa
    public List<Item> getItems() {
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        return lista;
    }

    //El siguiente método elimina un item de la varible de session
    public void delete(Item item) {
        //Se recupera la variable de session
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la lista existe...
        if (lista != null) {
            //Se busca el idProducto en la lista
            boolean existe = false;
            var posicion = -1;
            for (Item i : lista) {
                posicion++;
                if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                    existe = true;
                    break;
                }
            }
            //Validamos si es la primera vez que se ingresa el producto en el carrito
            if (existe) {
                lista.remove(posicion);
                session.setAttribute("listaItems", lista);
            }
        }
    }

    //El siguiente método actualiza la cantidad un item en la varible de session
    public void update(Item item) {
        //Se recupera la variable de session
        @SuppressWarnings("unchecked")
        List<Item> lista = (List) session.getAttribute("listaItems");
        //Se valida si la lista existe...
        if (lista != null) {
            //Se busca el idProducto en la lista
            boolean existe = false;
            for (Item i : lista) {
                if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                    i.setCantidad(item.getCantidad());
                    break;
                }
            }
        }
    }

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private VentaRepository ventaRepository;

    public void facturar() {
        //Se debe recuperar el usuario autenticado y obtener su idUsuario
        String username = "";
        var principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            if (principal != null) {
                username = principal.toString();
            }
        }

        if (username.isBlank()) {
            System.out.println("username en blanco...");
            return;
        }

        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            System.out.println("Usuario no existe en usuarios...");
            return;
        }

        //Se debe registrar la factura incluyendo el usuario
        Factura factura = new Factura(usuario.getIdUsuario());
        factura = facturaRepository.save(factura);

        //Se debe registrar las ventas de cada producto -actualizando existencias-
        @SuppressWarnings("unchecked")
        List<Item> listaItems = (List) session.getAttribute("listaItems");
        if (listaItems != null) {
            double total = 0;
            for (Item i : listaItems) {
                var producto = productoRepository.getReferenceById(i.getIdProducto());
                if (producto.getExistencias() >= i.getCantidad()) {
                    Venta venta = new Venta(factura.getIdFactura(),
                            i.getIdProducto(),
                            i.getPrecio(),
                            i.getCantidad());
                    ventaRepository.save(venta);
                    producto.setExistencias(producto.getExistencias() - i.getCantidad());
                    productoRepository.save(producto);
                    total += i.getCantidad() * i.getPrecio();
                }
            }

            //Se debe registrar el total de la venta en la factura
            factura.setTotal(total);
            facturaRepository.save(factura);

            //Se debe limpiar el carrito la lista...
            listaItems.clear();
        }
    }
}