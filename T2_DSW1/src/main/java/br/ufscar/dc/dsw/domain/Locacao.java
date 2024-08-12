package br.ufscar.dc.dsw.domain;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.validation.constraints.NotBlank;


@SuppressWarnings("serial")
@Entity
@Table(name = "Locacao")
public class Locacao extends AbstractEntity<Long> {
        @NotBlank
    @Column(nullable = false, unique = false, length = 256)
    private Date dia;
    
        @NotBlank
    @Column(nullable = false, unique = false, length = 256)
    private Time horario;

    @ManyToOne
    @JoinColumn(name = "CPF", nullable = false, foreignKey = @ForeignKey(name = "FK_Locacao_Cliente"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "CNPJ", nullable = false, foreignKey = @ForeignKey(name = "FK_Locacao_Locadora"))
    private Locadora locadora;


	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public Time getHorario() {
		return horario;
	}

	public void setHorario(Time horario) {
		this.horario = horario;
	}

}