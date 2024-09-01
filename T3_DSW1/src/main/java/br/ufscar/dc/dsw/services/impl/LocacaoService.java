package br.ufscar.dc.dsw.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.services.spec.ILocacaoService;
import br.ufscar.dc.dsw.dao.ILocacaoDAO;

@Service
@Transactional(readOnly = false)
public class LocacaoService implements ILocacaoService {

    @Autowired
    ILocacaoDAO locacaoDAO;

    public void salvar(Locacao locacao) {
        locacaoDAO.save(locacao);
    }

    public void excluir(Long id) {
        Optional<Locacao> optionalLocadora = locacaoDAO.findById(id);
        if (optionalLocadora.isPresent()) {
            Locacao locacao = optionalLocadora.get();

            locacaoDAO.delete(locacao);
		}
	}

    @Transactional(readOnly = true) 
    public Locacao buscarPorID(Long id) {
        return locacaoDAO.findById(id).orElseThrow(() -> new RuntimeException("Locacao não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Locacao> buscarTodos() {
        return locacaoDAO.findAll();
    }

    @Transactional(readOnly = true)
    public List<Locacao> buscarTodosPorCliente(Cliente cliente) {
        return locacaoDAO.findAllByCliente(cliente);
    }

    @Transactional(readOnly = true)
    public List<Locacao> buscarTodosPorLocadora(Locadora locadora) {
        return locacaoDAO.findAllByLocadora(locadora);
    }
}
