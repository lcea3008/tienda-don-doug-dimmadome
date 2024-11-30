package dimadon.business.tienda_don_doug_dimmadome.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.DetalleEntrada;
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

    public List<DetalleEntrada> registrarDetallesEntradaConKardex(List<DetalleEntrada> detallesEntrada) {
        List<DetalleEntrada> detallesGuardados = new ArrayList<>();

        for (DetalleEntrada detalleEntrada : detallesEntrada) {
            Producto producto = productoRepository.findById(detalleEntrada.getProducto().getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en la base de datos"));

            detalleEntrada.setProducto(producto);

            DetalleEntrada savedDetalleEntrada = repositoryDetalleEntrada.save(detalleEntrada);

            int nuevoStock = producto.getStock() + detalleEntrada.getCantidad();
            producto.setStock(nuevoStock);
            productoRepository.save(producto);

            String fechaFormateada = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Crear registro en Kardex
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
