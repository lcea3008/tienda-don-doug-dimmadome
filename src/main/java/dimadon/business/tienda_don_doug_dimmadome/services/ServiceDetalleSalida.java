package dimadon.business.tienda_don_doug_dimmadome.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.DetalleSalida;
import dimadon.business.tienda_don_doug_dimmadome.entities.Kardex;
import dimadon.business.tienda_don_doug_dimmadome.entities.Producto;
import dimadon.business.tienda_don_doug_dimmadome.entities.Salida;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryDetalleSalida;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryKardex;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryProducto;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositorySalida;
import dimadon.business.tienda_don_doug_dimmadome.values.Movimientos;
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
    public List<DetalleSalida> registrarDetallesSalidaConKardex(List<DetalleSalida> detallesSalida,
            String descripcionGeneral, Salida salida) {
        // Validar stock para todos los detalles antes de realizar cualquier operación
        for (DetalleSalida detalleSalida : detallesSalida) {
            Producto producto = repositoryProducto.findById(detalleSalida.getProducto().getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en la base de datos"));

            if (producto.getStock() < detalleSalida.getCantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto: " + producto.getNombre() +
                                ". Disponible: " + producto.getStock() +
                                ", Solicitado: " + detalleSalida.getCantidad());
            }
        }

        // Si la validación anterior pasa, procedemos a registrar los detalles
        List<DetalleSalida> detallesGuardados = new ArrayList<>();

        for (DetalleSalida detalleSalida : detallesSalida) {
            Producto producto = repositoryProducto.findById(detalleSalida.getProducto().getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en la base de datos"));

            detalleSalida.setProducto(producto);
            detalleSalida.setSalida(salida);
            detalleSalida.setDescripcion(descripcionGeneral);

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
            kardex.setTipoOperacion(Movimientos.SALIDA);
            kardex.setDescripcion(descripcionGeneral);
            kardex.setCantidadSalida(detalleSalida.getCantidad());
            kardex.setCostoUnitarioSalida(detalleSalida.getCostoUnitario());
            kardex.setCostoTotalSalida(detalleSalida.getCantidad() * detalleSalida.getCostoUnitario());
            kardex.setCantidadSaldo(nuevoStock);
            kardex.setCostoUnitarioSaldo(detalleSalida.getProducto().getPrecioUnitario());
            kardex.setCostoTotalSaldo(nuevoStock * detalleSalida.getProducto().getPrecioUnitario());

            repositoryKardex.save(kardex);
            detallesGuardados.add(savedDetalleSalida);
        }

        return detallesGuardados;
    }

    public ArrayList<DetalleSalida> obtenerDetalleSalidas() {
        return (ArrayList<DetalleSalida>) repositoryDetalleSalida.findAll();
    }
}
