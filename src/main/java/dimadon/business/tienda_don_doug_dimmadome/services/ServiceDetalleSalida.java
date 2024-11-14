package dimadon.business.tienda_don_doug_dimmadome.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.Repository.RepositoryDetalleSalida;
import dimadon.business.tienda_don_doug_dimmadome.Repository.RepositoryKardex;
import dimadon.business.tienda_don_doug_dimmadome.Repository.RepositoryProducto;
import dimadon.business.tienda_don_doug_dimmadome.Repository.RepositorySalida;

import dimadon.business.tienda_don_doug_dimmadome.entities.DetalleSalida;
import dimadon.business.tienda_don_doug_dimmadome.entities.Kardex;
import dimadon.business.tienda_don_doug_dimmadome.entities.Producto;
import jakarta.transaction.Transactional;

@Service
public class ServiceDetalleSalida {

    @Autowired
    RepositoryDetalleSalida repositoryDetalleSalida;

    @Autowired
    RepositoryKardex repositoryKardex;

    @Autowired
    RepositoryProducto repositoryProducto;

    @Autowired
    RepositorySalida repositorySalida;

    public int obtenerUltimoIdSalida() {
        int ultimoId = repositorySalida.findMaxIdSalida();
        return ultimoId; // Retorna el último ID de salida
    }

    @Transactional
    public List<DetalleSalida> registrarDetallesSalidaConKardex(List<DetalleSalida> detallesSalida) {
        List<DetalleSalida> detallesGuardados = new ArrayList<>();

        for (DetalleSalida detalleSalida : detallesSalida) {
            Producto producto = repositoryProducto.findById(detalleSalida.getProducto().getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en la base de datos"));

            detalleSalida.setProducto(producto);

            DetalleSalida savedDetalleSalida = repositoryDetalleSalida.save(detalleSalida);
                
            int nuevoStock = producto.getStock() - detalleSalida.getCantidad();
            producto.setStock(nuevoStock);
            repositoryProducto.save(producto);

            String fechaFormateada = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Crear registro en Kardex
            Kardex kardex = new Kardex();
            kardex.setProducto(producto);
            kardex.setNombreProducto(detalleSalida.getProducto().getNombre());
            kardex.setFecha(fechaFormateada);
            kardex.setTipoOperacion("Salida");
            kardex.setDescripcion(detalleSalida.getDescripcion());
            kardex.setCantidadSalida(detalleSalida.getCantidad());
            kardex.setCostoUnitarioSalida(detalleSalida.getCostoUnitario());
            kardex.setCostoTotalSalida(detalleSalida.getCantidad() * detalleSalida.getCostoUnitario());
            kardex.setCantidadSaldo(detalleSalida.getProducto().getStock());
            kardex.setCostoUnitarioSaldo(detalleSalida.getProducto().getPrecioUnitario());
            kardex.setCostoTotalSaldo(
                    detalleSalida.getProducto().getStock() * detalleSalida.getProducto().getPrecioUnitario());

            repositoryKardex.save(kardex);
            detallesGuardados.add(savedDetalleSalida);
        }

        return detallesGuardados;
    }

    public ArrayList<DetalleSalida> obtenerDetalleSalidas() {
        return (ArrayList<DetalleSalida>) repositoryDetalleSalida.findAll();
    }
}
