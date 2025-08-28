package co.com.pragma.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String VALID_AMOUNT = "El monto es un campo requerido.";
    public static final String VALID_AMOUNT_MIN = "el valor mínimo del monto es 0.1.";

    public static final String VALID_TERM = "El plazo en meses es un campo requerido.";
    public static final String VALID_TERM_MIN = "El plazo en meses debe ser mayor o igual a 1.";

    public static final String VALID_DOCUMENT = "El número de documento es un campo requerido.";
    public static final String VALID_DOCUMENT_SIZE = "El número de documento no puede tener más de 20 caracteres.";

    public static final String VALID_LOAN_TYPE = "El tipo de crédito es un campo requerido.";
    public static final String VALID_LOAN_TYPE_NUMBER = "El tipo de crédito actual no es válido.";

}
