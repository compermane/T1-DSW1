package br.ufscar.dc.dsw.services.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Locadora;

public interface ILocadoraService {
    Locadora buscarPorID(Long id);
    List<Locadora> buscarTodos();
    List<Locadora> buscarPorCidade(String cidade);
    void salvar(Locadora locadora);
    void excluir(Long id);
}
