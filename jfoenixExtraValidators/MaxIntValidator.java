package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;

/**
 * 最大整数校验类<p></p>
 * x <= max
 */
public class MaxIntValidator extends ValidatorBase {
    public static void createValidator(TextInputControl validateControl, String message, int max) {
        new MaxIntValidator(validateControl, message, max);
    }

    private int max;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param max 最大值(>)
     */
    private MaxIntValidator(TextInputControl validateControl, String message, int max) {
        message = message + ",最大值=" + max;
        this.setMessage(message);
        this.max = max;
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
            int i = Integer.parseInt(text);
            if (i > max) {
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
        MaxIntValidator that = (MaxIntValidator) o;
        return max == that.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(max);
    }
}
