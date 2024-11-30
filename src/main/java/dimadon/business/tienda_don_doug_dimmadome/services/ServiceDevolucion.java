package dimadon.business.tienda_don_doug_dimmadome.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.Devolucion;
import dimadon.business.tienda_don_doug_dimmadome.entities.Kardex;
import dimadon.business.tienda_don_doug_dimmadome.entities.Producto;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryDevolucion;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryKardex;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryProducto;
import jakarta.transaction.Transactional;

@Service
public class ServiceDevolucion {

    @Autowired
    RepositoryDevolucion repositoryDevolucion;

    @Autowired
    RepositoryProducto repositoryProducto;

    @Autowired
    RepositoryKardex repositoryKardex;

    public ArrayList<Devolucion> obtenerDevolucion() {
        return (ArrayList<Devolucion>) repositoryDevolucion.findAll();
    }

    @Transactional
    public Devolucion registrarDevolucion(Devolucion devolucion) {
        Producto producto = repositoryProducto.findById(devolucion.getProducto().getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en la base de datos"));

        devolucion.setProducto(producto);

        Devolucion savedDevolucion = repositoryDevolucion.save(devolucion);

        String fechaFormateada = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (devolucion.getTipoDevolucion().getIdTipoDevolucion() == 1) {

            // añadir a tabla de kardex
            Kardex kardex = new Kardex();
            kardex.setProducto(producto);
            kardex.setNombreProducto(devolucion.getProducto().getNombre());
            kardex.setFecha(fechaFormateada);
            kardex.setTipoOperacion("Devolución" + devolucion.getTipoDevolucion().getNombre());
            kardex.setDescripcion(devolucion.getDescripcion());
            kardex.setCantidadSalida(devolucion.getCantidad());
            kardex.setCostoUnitarioSalida(devolucion.getProducto().getPrecioUnitario());
            kardex.setCostoTotalSalida(devolucion.getCantidad() * devolucion.getProducto().getPrecioUnitario());
            kardex.setCantidadSaldo(devolucion.getProducto().getStock());
            kardex.setCostoUnitarioSaldo(devolucion.getProducto().getPrecioUnitario());
            kardex.setCostoTotalSaldo(
                    devolucion.getProducto().getStock() * devolucion.getProducto().getPrecioUnitario());

            repositoryKardex.save(kardex);
        } else if (devolucion.getTipoDevolucion().getIdTipoDevolucion() == 2) {
            int nuevoStock = producto.getStock() + devolucion.getCantidad();
            producto.setStock(nuevoStock);
            repositoryProducto.save(producto);

            // añadir a tabla de kardex
            Kardex kardex = new Kardex();
            kardex.setProducto(producto);
            kardex.setNombreProducto(devolucion.getProducto().getNombre());
            kardex.setFecha(fechaFormateada);
            kardex.setTipoOperacion("Devolución" + devolucion.getTipoDevolucion().getNombre());
            kardex.setDescripcion(devolucion.getDescripcion());
            kardex.setCantidadEntrada(devolucion.getCantidad());
            kardex.setCostoUnitarioEntrada(devolucion.getProducto().getPrecioUnitario());
            kardex.setCostoTotalEntrada(devolucion.getCantidad() * devolucion.getProducto().getPrecioUnitario());
            kardex.setCantidadSaldo(devolucion.getProducto().getStock());
            kardex.setCostoUnitarioSaldo(devolucion.getProducto().getPrecioUnitario());
            kardex.setCostoTotalSaldo(
                    devolucion.getProducto().getStock() * devolucion.getProducto().getPrecioUnitario());

            repositoryKardex.save(kardex);
        }

        return savedDevolucion;
    }

}
