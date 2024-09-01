package br.ufscar.dc.dsw.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueDocumentoValidator.class)
public @interface UniqueDocumento {
    String message() default "Documento já cadastrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    DocumentoType type();

    enum DocumentoType {
        DOCUMENTO, CPF, CNPJ
    }
}