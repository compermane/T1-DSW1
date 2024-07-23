package br.ufscar.dc.dsw.domain;

import java.sql.Date;

public class Cliente extends Usuario {
    private String telefone;
    private String sexo;
    private Date dataNascimento;

    public Cliente(int id, String documento, String email, String password, String nome, 
                    boolean admin, boolean locadora, String telefone, String sexo, Date dataNascimento) {
        super(id, documento, email, password, nome, admin, locadora);
        this.telefone = telefone;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getSexo() {
        return this.sexo;
    }

    public Date getDataNascimento() {
        return this.dataNascimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
