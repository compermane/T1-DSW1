package br.ufscar.dc.dsw.services.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.domain.Cliente;

public interface ILocacaoService {
    Locacao buscarPorID(Long id);

	List<Locacao> buscarTodos();
	List<Locacao> buscarTodosPorCliente(Cliente cliente);
    List<Locacao> buscarTodosPorLocadora(Locadora locadora);

    void salvar(Locacao locacao);
    void excluir(Long id);
}
