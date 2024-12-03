package dimadon.business.tienda_don_doug_dimmadome.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.DetalleEntrada;
import dimadon.business.tienda_don_doug_dimmadome.entities.Entrada;
import dimadon.business.tienda_don_doug_dimmadome.entities.Kardex;
import dimadon.business.tienda_don_doug_dimmadome.entities.Producto;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryDetalleEntrada;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryEntrada;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryKardex;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryProducto;
import jakarta.transaction.Transactional;

@Service
public class ServiceDetalleEntrada {

    @Autowired
    private RepositoryDetalleEntrada repositoryDetalleEntrada;

    @Autowired
    private RepositoryKardex repositoryKardex;

    @Autowired
    private RepositoryProducto productoRepository;

    @Autowired
    RepositoryEntrada repositoryEntrada;

    public int obtenerUltimoIdEntrada() {
        int ultimoId = repositoryEntrada.findMaxIdEntrada();
        return ultimoId; // Retorna el último ID de salida
    }

    @Transactional
    public List<DetalleEntrada> registrarDetallesEntradaConKardex(List<DetalleEntrada> detallesEntrada, Entrada entrada,
            String descripcion) {
        // Validar stock para todos los detalles antes de realizar cualquier operación
        for (DetalleEntrada detalleEntrada : detallesEntrada) {
            Producto producto = productoRepository.findById(detalleEntrada.getProducto().getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en la base de datos"));

            // Verificar que la cantidad de entrada sea positiva, en caso de no, se lanzará
            // un error
            if (detalleEntrada.getCantidad() <= 0) {
                throw new IllegalArgumentException(
                        "La cantidad de entrada para el producto " + producto.getNombre() + " debe ser mayor a cero.");
            }
        }

        // Si la validación anterior pasa, procedemos a registrar los detalles
        List<DetalleEntrada> detallesGuardados = new ArrayList<>();

        for (DetalleEntrada detalleEntrada : detallesEntrada) {
            Producto producto = productoRepository.findById(detalleEntrada.getProducto().getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en la base de datos"));

            detalleEntrada.setProducto(producto);
            detalleEntrada.setEntrada(entrada);
            detalleEntrada.setDescripcion(descripcion);

            // Actualizar la cantidad de stock del producto
            int nuevoStock = producto.getStock() + detalleEntrada.getCantidad();
            producto.setStock(nuevoStock);
            productoRepository.save(producto);

            // Registrar el detalle de entrada en la base de datos
            DetalleEntrada savedDetalleEntrada = repositoryDetalleEntrada.save(detalleEntrada);

            // Crear el registro en Kardex
            String fechaFormateada = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Kardex kardex = new Kardex();
            kardex.setProducto(producto);
            kardex.setNombreProducto(producto.getNombre());
            kardex.setFecha(fechaFormateada);
            kardex.setTipoOperacion("Entrada");
            kardex.setDescripcion(detalleEntrada.getDescripcion());
            kardex.setCantidadEntrada(detalleEntrada.getCantidad());
            kardex.setCostoUnitarioEntrada(detalleEntrada.getCostoUnitario());
            kardex.setCostoTotalEntrada(detalleEntrada.getCostoUnitario() * detalleEntrada.getCantidad());
            kardex.setCantidadSaldo(nuevoStock);
            kardex.setCostoUnitarioSaldo(detalleEntrada.getCostoUnitario());
            kardex.setCostoTotalSaldo(detalleEntrada.getCostoUnitario() * nuevoStock);
            repositoryKardex.save(kardex);

            detallesGuardados.add(savedDetalleEntrada);
        }

        return detallesGuardados;
    }

    public ArrayList<DetalleEntrada> obtenerDetalleEntradas() {
        return (ArrayList<DetalleEntrada>) repositoryDetalleEntrada.findAll();
    }
}
