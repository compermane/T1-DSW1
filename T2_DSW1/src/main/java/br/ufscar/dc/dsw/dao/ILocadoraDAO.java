package br.ufscar.dc.dsw.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Locadora;

@SuppressWarnings("unchecked")
public interface ILocadoraDAO extends CrudRepository<Locadora, Long>{

	Optional<Locadora> findById(Long id);
	List<Locadora> findAll();
	
	Locadora save(Locadora locadora);

	void deleteById(Long id);

	@Query("SELECT l FROM Locadora l WHERE l.cnpj = :cnpj")
	public Locadora findByCnpj(@Param("cnpj") String cnpj);
}