package br.ufscar.dc.dsw.services.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cliente;

public interface IClienteService {
    Cliente buscarPorID(Long id);
    List<Cliente> buscarTodos();
    void salvar(Cliente cliente);
    void excluir(Long id);
}
