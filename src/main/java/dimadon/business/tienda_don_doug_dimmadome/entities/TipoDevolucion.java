package dimadon.business.tienda_don_doug_dimmadome.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_devolucion")
public class TipoDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_devolucion")
    private int idTipoDevolucion;

    @Column(name = "nombre_tipo", nullable = false)
    private String nombre;

    public int getIdTipoDevolucion() {
        return idTipoDevolucion;
    }

    public void setIdTipoDevolucion(int idTipoDevolucion) {
        this.idTipoDevolucion = idTipoDevolucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
