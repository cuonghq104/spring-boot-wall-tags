package ptit.cuonghq.walltag.utils;

import org.apache.commons.validator.routines.EmailValidator;

import java.text.NumberFormat;
import java.util.Locale;

public class Validate {

    public static boolean isValidatedEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static String convertCurrencyToString(long number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }
}
