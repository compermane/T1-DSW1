package br.ufscar.dc.dsw.command;

public class LocadoraCommand {
    private String crudAction;
    private String id;
    private String email;
    private String senha;
    private String nome;
    private String cnpj;
    private String cidade;

    public String getCrudAction() {
        return crudAction;
    }

    public void setCrudAction(String action) {
        this.crudAction = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
