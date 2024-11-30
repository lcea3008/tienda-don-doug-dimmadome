package dimadon.business.tienda_don_doug_dimmadome.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.TipoDevolucion;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryTipoDevolucion;

@Service
public class ServiceTipoDevolucion {

    @Autowired
    RepositoryTipoDevolucion repositoryTipoDevolucion;

    public ArrayList<TipoDevolucion> obtenerTipoDevolucion() {
        return (ArrayList<TipoDevolucion>) repositoryTipoDevolucion.findAll();
    }

    public TipoDevolucion insertarTipoDevolucion(TipoDevolucion tipoDevolucion) {
        return this.repositoryTipoDevolucion.save(tipoDevolucion);
    }
}
