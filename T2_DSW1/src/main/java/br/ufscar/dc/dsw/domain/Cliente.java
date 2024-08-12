package br.ufscar.dc.dsw.domain;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import br.ufscar.dc.dsw.validation.UniqueDocumento;

@SuppressWarnings("serial")
@Entity
@Table(name = "Cliente")
public class Cliente extends Usuario {

        @NotBlank
        @UniqueDocumento(type = UniqueDocumento.DocumentoType.CPF, message = "CPF já cadastrado")
    @Column(nullable = false, unique = true, length = 11)
    private String CPF;

        @NotBlank
    @Column(nullable = false, unique = false, length = 11)
    private String telefone;

        @NotBlank
    @Column(nullable = false, unique = false)
    private Date data_nascimento;

        @NotBlank
    @Column(nullable = false, unique = false, length = 1)
    private String sexo;
    
	@OneToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	
	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return data_nascimento;
	}

	public void setDataNascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("CPF: " + CPF + ", ");
		sb.append("Telefone: " + telefone + ", ");
		sb.append("Data de nascimento: " + data_nascimento.toString() + ", ");
        sb.append("Sexo: " + sexo + ", ");
		sb.append("]");
		return sb.toString(); 
	}
}