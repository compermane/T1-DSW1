package br.ufscar.dc.dsw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import br.ufscar.dc.dsw.validation.UniqueDocumento;

@SuppressWarnings("serial")
@Entity
@Table(name = "Locadora")
public class Locadora extends Usuario {

        @NotBlank
        @UniqueDocumento(type = UniqueDocumento.DocumentoType.CNPJ, message = "CNPJ já cadastrado")
    @Column(name = "CNPJ", nullable = false, unique = true, length = 14)
    private String cnpj;

        @NotBlank
    @Column(nullable = false, unique = false, length = 256)
    private String cidade;

	
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("CNPJ: " + cnpj + ", ");
		sb.append("Cidade: " + cidade);
		sb.append("]");
		return sb.toString(); 
	}
}