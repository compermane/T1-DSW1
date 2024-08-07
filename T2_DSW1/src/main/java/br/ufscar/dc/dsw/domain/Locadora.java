package br.ufscar.dc.dsw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import br.ufscar.dc.dsw.validation.UniqueDocumento;

@SuppressWarnings("serial")
@Entity
@Table(name = "Locadora")
public class Locadora extends AbstractEntity<Long> {

        @NotBlank
        @UniqueDocumento(type = UniqueDocumento.DocumentoType.CNPJ, message = "CNPJ já cadastrado")
    @Column(nullable = false, unique = true, length = 14)
    private String CNPJ;

        @NotBlank
    @Column(nullable = false, unique = false, length = 256)
    private String cidade;
    
	@OneToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	
	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String CNPJ) {
		this.CNPJ = CNPJ;
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
		sb.append("CNPJ: " + CNPJ + ", ");
		sb.append("Cidade: " + cidade);
		sb.append("]");
		return sb.toString(); 
	}
}