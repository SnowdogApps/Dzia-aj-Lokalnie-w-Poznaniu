package pl.snowdog.dzialajlokalnie.util;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by bartek on 06.08.15.
 */
public class NotEmptyValidator {

    public static boolean validate(EditText et, TextInputLayout til, int warningStringId) {
        boolean isInputValid = true;
        if (et.getText().toString().length() == 0) {
            til.setErrorEnabled(true);
            til.setError(et.getContext().getString(warningStringId));
            isInputValid = false;
        } else {
            til.setErrorEnabled(false);
        }
        return isInputValid;
    }
}
