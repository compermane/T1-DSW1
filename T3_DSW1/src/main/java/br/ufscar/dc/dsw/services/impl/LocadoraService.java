package br.ufscar.dc.dsw.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.services.spec.ILocadoraService;
import br.ufscar.dc.dsw.dao.ILocadoraDAO;

@Service
@Transactional(readOnly = false)
public class LocadoraService implements ILocadoraService {

    @Autowired
    ILocadoraDAO locadoraDAO;

    public void salvar(Locadora locadora) {
        locadoraDAO.save(locadora);
    }

    public void excluir(Long id) {
        Optional<Locadora> optionalLocadora = locadoraDAO.findById(id);
        if (optionalLocadora.isPresent()) {
            Locadora locadora = optionalLocadora.get();

            locadoraDAO.delete(locadora);
		}
	}

    @Transactional(readOnly = true) 
    public Locadora buscarPorID(Long id) {
        return locadoraDAO.findById(id).orElseThrow(() -> new RuntimeException("Locadora não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Locadora> buscarTodos() {
        return locadoraDAO.findAll();
    }

    @Transactional(readOnly = true) 
    public List<Locadora> buscarPorCidade(String cidade) {
        return locadoraDAO.findByCidade(cidade);
    }
}
