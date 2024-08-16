package br.ufscar.dc.dsw.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.domain.Cliente;

@SuppressWarnings("unchecked")
public interface ILocacaoDAO extends CrudRepository<Locacao, Long>{

	Optional<Locacao> findById(Long id);
	List<Locacao> findAll();
	
	Locacao save(Locacao locacao);

	void deleteById(Long id);

    List<Locacao> findAllByCliente(Cliente cliente);
    List<Locacao> findAllByLocadora(Locadora locadora);
}