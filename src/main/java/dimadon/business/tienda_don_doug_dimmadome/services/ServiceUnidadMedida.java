package dimadon.business.tienda_don_doug_dimmadome.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.UnidadMedida;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryUnidadMedida;

@Service
public class ServiceUnidadMedida {

    @Autowired
    private RepositoryUnidadMedida repositoryUnidadMedida;

    public ArrayList<UnidadMedida> obtnerUnidadMedida() {
        return (ArrayList<UnidadMedida>) repositoryUnidadMedida.findAll();
    }

    public UnidadMedida insertarUnidadMedida(UnidadMedida unidadMedida) {
        return this.repositoryUnidadMedida.save(unidadMedida);
    }
}
