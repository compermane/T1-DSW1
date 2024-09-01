package br.ufscar.dc.dsw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.ILocadoraDAO;

@Component
public class UniqueDocumentoValidator implements ConstraintValidator<UniqueDocumento, String> {

	@Autowired
	private ILocadoraDAO locadoraDAO;

	@Autowired
	private IClienteDAO clienteDAO;

	private UniqueDocumento.DocumentoType type;

	@Override
	public void initialize(UniqueDocumento constraintAnnotation) {
		this.type = constraintAnnotation.type();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        switch (type) {
            case CPF:
                return clienteDAO == null || clienteDAO.findByCPF(value) == null;
            case CNPJ:
                return locadoraDAO == null || locadoraDAO.findByCnpj(value) == null;
            default:
                return true;
        }
    }
}