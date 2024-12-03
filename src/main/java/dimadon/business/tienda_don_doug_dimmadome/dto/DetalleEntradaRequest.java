package dimadon.business.tienda_don_doug_dimmadome.dto;

import java.util.List;

import dimadon.business.tienda_don_doug_dimmadome.entities.DetalleEntrada;
import dimadon.business.tienda_don_doug_dimmadome.entities.Entrada;

public class DetalleEntradaRequest {
    
    private String descripcion;
    private List<DetalleEntrada> detallesEntrada;
    private Entrada entrada;

    // Getters y Setters

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<DetalleEntrada> getDetallesEntrada() {
        return detallesEntrada;
    }

    public void setDetallesEntrada(List<DetalleEntrada> detallesEntrada) {
        this.detallesEntrada = detallesEntrada;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    
}
