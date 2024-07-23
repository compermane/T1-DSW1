package br.ufscar.dc.dsw.domain;

public class Usuario {
    private int id;
    private String documento;   // Pode ser CPF ou CNPJ
    private String email;
    private String password;
    private String nome;
    private boolean admin;
    private boolean locadora;

    public Usuario(int id) {
        this.id = id;
    }
    
    public Usuario(String email, String documento, String password, String nome, boolean admin, boolean locadora) {
        super();
        this.email = email;
        this.documento = documento;
        this.password = password;
        this.nome = nome;
        this.admin = admin;
        this.locadora = locadora;
    }

    public Usuario(int id, String documento, String email, String password, String nome, boolean admin, boolean locadora) {
        super();
        this.id = id;
        this.documento = documento;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.admin = admin;
        this.locadora = locadora;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return this.documento;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNome() {
        return this.nome;
    }

    public boolean getAdmin() {
        return this.admin;
    }

    public boolean getIsLocadora() {
        return this.locadora;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setLocadora(boolean locadora) {
        this.admin = locadora;
    }
}
