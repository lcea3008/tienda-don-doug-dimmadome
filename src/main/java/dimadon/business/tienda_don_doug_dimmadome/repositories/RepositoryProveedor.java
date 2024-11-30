package dimadon.business.tienda_don_doug_dimmadome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dimadon.business.tienda_don_doug_dimmadome.entities.Proveedor;

@Repository
public interface RepositoryProveedor extends JpaRepository<Proveedor, Integer> {

}
