package br.ufscar.dc.dsw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.ufscar.dc.dsw.validation.UniqueEmail;

@SuppressWarnings("serial")
@Entity
@Table(name = "Usuario")

@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario extends AbstractEntity<Long> {
		@NotBlank
		@UniqueEmail(message = "Email já cadastrado")
	@Column(nullable = false, unique = true, length = 256)
	private String username;
    
		@NotBlank
	@Column(nullable = false, unique = false, length = 256)
	private String password;
	
		@NotBlank
	@Column(nullable = false, unique = false, length = 256)
	private String nome;

		@NotNull
	@Column(nullable = false, unique = false)
	private boolean isAdmin;

		@NotBlank
	@Column(nullable = false, unique = false, length = 45)
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		sb.append("Email: " + username + ", ");
		sb.append("Nome: " + nome + ", ");
		sb.append("Admin: " + ((isAdmin) ? "sim" : "não") + ", ");
		sb.append("Tipo: " + role);
		sb.append("]");
		return sb.toString(); 
	}
}