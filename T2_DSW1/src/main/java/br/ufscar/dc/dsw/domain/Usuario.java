package br.ufscar.dc.dsw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import br.ufscar.dc.dsw.validation.UniqueDocumento;
import br.ufscar.dc.dsw.validation.UniqueEmail;

@SuppressWarnings("serial")
@Entity
@Table(name = "Usuario")
public class Usuario extends AbstractEntity<Long> {

		@NotBlank
		@UniqueDocumento(type = UniqueDocumento.DocumentoType.DOCUMENTO, message = "Documento já cadastrado")	
	@Column(nullable = false, unique = true, length = 20)
	private String documento;

		@NotBlank
		@UniqueEmail(message = "Email já cadastrado")
	@Column(nullable = false, unique = true, length = 256)
	private String email;
    
		@NotBlank
	@Column(nullable = false, unique = false, length = 256)
	private String senha;
	
		@NotBlank
	@Column(nullable = false, unique = false, length = 256)
	private String nome;

		@NotBlank
	@Column(nullable = false, unique = false)
	private boolean isAdmin;

		@NotBlank
	@Column(nullable = false, unique = false, length = 45)
	private String role;
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean admin) {
		this.isAdmin = admin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("Documento: " + documento + ", ");
		sb.append("Email: " + email + ", ");
		sb.append("Nome: " + nome + ", ");
		sb.append("Admin: " + ((isAdmin) ? "sim" : "não") + ", ");
		sb.append("Tipo: " + role);
		sb.append("]");
		return sb.toString(); 
	}
}