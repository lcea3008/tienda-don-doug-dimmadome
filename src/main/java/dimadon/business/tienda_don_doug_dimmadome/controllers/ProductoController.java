package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dimadon.business.tienda_don_doug_dimmadome.entities.Producto;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceProducto;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ServiceProducto serviceProducto;

    @GetMapping("/obtener")
    public ArrayList<Producto> obtenerProducto() {
        return serviceProducto.obtenerProducto();
    }

    @PostMapping("/insertar")
    public Producto guardarProducto(@RequestBody Producto producto) {
        return this.serviceProducto.insertarProducto(producto);
    }

    // actualiza segun el id
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable("id") int id, @RequestBody Producto producto) {
        Producto productoActualizado = serviceProducto.actualizarProducto(id, producto);
        if (productoActualizado != null) {
            return ResponseEntity.ok(productoActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/{id}/estado")
    public Producto cambiarEstadoProductoPorId(@PathVariable("id") int idProducto,
            @RequestParam("estado") String estado) {
        return serviceProducto.cambiarEstadoProductoPorId(idProducto, estado);
    }

    @GetMapping("/estado")
    public List<Producto> obtenerProductosPorEstado(@RequestParam("estado") String estado) {
        return serviceProducto.obtenerProductosPorEstado(estado);
    }

}
