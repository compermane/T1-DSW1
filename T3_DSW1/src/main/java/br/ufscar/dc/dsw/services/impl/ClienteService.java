package br.ufscar.dc.dsw.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.services.spec.IClienteService;
import br.ufscar.dc.dsw.dao.IClienteDAO;

@Service
@Transactional(readOnly = false)
public class ClienteService implements IClienteService {

    @Autowired
    IClienteDAO clienteDAO;

    public void salvar(Cliente cliente) {
        clienteDAO.save(cliente);
    }

    public void excluir(Long id) {
        Optional<Cliente> optionalLocadora = clienteDAO.findById(id);
        if (optionalLocadora.isPresent()) {
            Cliente cliente = optionalLocadora.get();

            clienteDAO.delete(cliente);
		}
	}

    @Transactional(readOnly = true) 
    public Cliente buscarPorID(Long id) {
        return clienteDAO.findById(id).orElseThrow(() -> new RuntimeException("Locadora não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarTodos() {
        return clienteDAO.findAll();
    }
}
