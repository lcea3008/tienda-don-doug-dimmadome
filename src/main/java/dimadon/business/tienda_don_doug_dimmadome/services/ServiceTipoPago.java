package dimadon.business.tienda_don_doug_dimmadome.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.TipoPago;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryTipoPago;

@Service
public class ServiceTipoPago {

    @Autowired
    RepositoryTipoPago repositoryTipoPago;

    public ArrayList<TipoPago> obtnerTipoPago() {
        return (ArrayList<TipoPago>) repositoryTipoPago.findAll();
    }

    public TipoPago insertarTipoPago(TipoPago tipoPago) {
        return this.repositoryTipoPago.save(tipoPago);
    }
}
