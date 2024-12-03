package dimadon.business.tienda_don_doug_dimmadome.dto;

import java.util.List;

import dimadon.business.tienda_don_doug_dimmadome.entities.DetalleSalida;
import dimadon.business.tienda_don_doug_dimmadome.entities.Salida;

public class DetallesSalidaRequest {
    private String descripcion;
    private List<DetalleSalida> detallesSalida;
    private Salida salida;

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<DetalleSalida> getDetallesSalida() {
        return detallesSalida;
    }

    public void setDetallesSalida(List<DetalleSalida> detallesSalida) {
        this.detallesSalida = detallesSalida;
    }

    public Salida getSalida() {
        return salida;
    }

    public void setSalida(Salida salida) {
        this.salida = salida;
    }
}
