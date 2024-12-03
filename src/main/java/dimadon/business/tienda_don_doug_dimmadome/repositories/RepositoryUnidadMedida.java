package dimadon.business.tienda_don_doug_dimmadome.repositories;

import org.springframework.stereotype.Repository;

import dimadon.business.tienda_don_doug_dimmadome.entities.UnidadMedida;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RepositoryUnidadMedida extends JpaRepository<UnidadMedida, Integer> {

}
