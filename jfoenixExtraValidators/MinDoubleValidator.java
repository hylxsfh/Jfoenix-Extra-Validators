package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;

/**
 * 最小浮点数校验类<p></p>
 * x >= min
 */
public class MinDoubleValidator extends ValidatorBase {

    public static void createValidator(TextInputControl validateControl, String message, double min) {
        new MinDoubleValidator(validateControl, message, min);
    }

    private double min;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param min 最小值
     */
    private MinDoubleValidator(TextInputControl validateControl, String message, double min) {
        message = message + ",最小值=" + min;
        this.setMessage(message);
        this.min = min;
        setIcon(new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE));

        IFXValidatableControl control = ((IFXValidatableControl) validateControl);
        if (!control.getValidators().contains(this)) {
            control.getValidators().add(this);
        }
        validateControl.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        control.validate();
                    }
                }
        );
        if (validateControl instanceof TextField) {
            ((TextField)validateControl).setOnAction(actionEvent -> {
                control.validate();
            });
        }
    }

    @Override
    protected void eval() {
        if (!(srcControl.get() instanceof TextInputControl)) {
            return;
        }
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText();
        try {
            hasErrors.set(true);
            if ((text == null) || text.isEmpty()) {
                return;
            }
            double d = Double.parseDouble(text);
            if (d < min) {
                return;
            }
            hasErrors.set(false);
        } catch (Exception e) {
            hasErrors.set(true);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MinDoubleValidator that = (MinDoubleValidator) o;
        return Double.compare(that.min, min) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min);
    }
}
