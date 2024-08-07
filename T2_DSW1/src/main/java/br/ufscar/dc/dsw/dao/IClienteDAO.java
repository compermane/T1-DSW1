package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Cliente;

@SuppressWarnings("unchecked")
public interface IClienteDAO extends CrudRepository<Cliente, Long>{

	Cliente findById(long id);
	List<Cliente> findAll();
	
	Cliente save(Cliente usuario);

	void deleteById(Long id);

	@Query("SELECT c FROM Cliente c WHERE c.CPF = :CPF")
	public Cliente findByCPF(@Param("CPF") String CPF);
}