package dimadon.business.tienda_don_doug_dimmadome.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.Categoria;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryCategoria;

@Service
public class ServiceCategoria {

    @Autowired
    RepositoryCategoria repositoryCategoria;

    public ArrayList<Categoria> obtnerCategoria() {
        return (ArrayList<Categoria>) repositoryCategoria.findAll();
    }

    public Categoria insertarCategoria(Categoria categoria) {
        return this.repositoryCategoria.save(categoria);
    }
}
