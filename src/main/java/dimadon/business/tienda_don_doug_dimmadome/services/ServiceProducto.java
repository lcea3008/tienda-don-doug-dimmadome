package dimadon.business.tienda_don_doug_dimmadome.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.Kardex;
import dimadon.business.tienda_don_doug_dimmadome.entities.Producto;
import dimadon.business.tienda_don_doug_dimmadome.entities.Usuario;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryKardex;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryProducto;

@Service
public class ServiceProducto {

    @Autowired
    RepositoryProducto repositoryProducto;

    @Autowired
    RepositoryKardex repositoryKardex;

    public ArrayList<Producto> obtenerProducto() {

        return (ArrayList<Producto>) repositoryProducto.findAll();
    }


    public Producto insertarProducto(Producto producto) {
        // Insertar el producto
        Producto nuevoProducto = repositoryProducto.save(producto);

        // guardar el registro en kardex
        Kardex kardex = new Kardex();
        kardex.setProducto(nuevoProducto);
        kardex.setNombreProducto(producto.getNombre());
        kardex.setTipoOperacion("Entrada");
        kardex.setDescripcion("Don Doug Dimadon");
        kardex.setCantidadEntrada(producto.getStock());
        kardex.setCostoUnitarioEntrada(producto.getPrecioUnitario());
        kardex.setCostoTotalEntrada(producto.getPrecioUnitario() * producto.getStock());
        kardex.setCantidadSaldo(producto.getStock());
        kardex.setCostoUnitarioSaldo(producto.getPrecioUnitario());
        kardex.setCostoTotalSaldo(producto.getPrecioUnitario() * producto.getStock());
        repositoryKardex.save(kardex);

        return nuevoProducto;
    }

    // actualizar usuario por id
    public Producto actualizarProducto(int id, Producto productoActualizado) {
        Optional<Producto> productoExistenteOpt = repositoryProducto.findById(id);
        if (productoExistenteOpt.isPresent()) {
            Producto productoExistente = productoExistenteOpt.get();

            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setPrecioUnitario(productoActualizado.getPrecioUnitario());
            productoExistente.setStock(productoActualizado.getStock());
            productoExistente.setFechaVencimiento(productoActualizado.getFechaVencimiento());
            productoExistente.setCategoria(productoActualizado.getCategoria());
            productoExistente.setEstado(productoActualizado.getEstado());

            return repositoryProducto.save(productoExistente);
        } else {
            return null;
        }
    }

    // camabiar estado del producto
    public Producto cambiarEstadoProducto(int idProducto, String nuevoEstado) {
        Producto producto = repositoryProducto.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id: " + idProducto));

        producto.setEstado(nuevoEstado);
        return repositoryProducto.save(producto);
    }

    // obtener producto por estado
    public List<Producto> obtenerProductosPorEstado(String estado) {
        if (!estado.equalsIgnoreCase("activo") && !estado.equalsIgnoreCase("inactivo")) {
            throw new IllegalArgumentException("Estado no válido. Debe ser 'activo' o 'inactivo'.");
        }

        // Obtener productos según el estado
        return repositoryProducto.findByEstado(estado);
    }

}
