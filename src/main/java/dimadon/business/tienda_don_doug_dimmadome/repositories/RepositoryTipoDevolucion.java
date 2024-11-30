package dimadon.business.tienda_don_doug_dimmadome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dimadon.business.tienda_don_doug_dimmadome.entities.TipoDevolucion;

@Repository
public interface RepositoryTipoDevolucion extends JpaRepository<TipoDevolucion, Integer> {

}
