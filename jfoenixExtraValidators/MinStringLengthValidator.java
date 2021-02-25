package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;

/**
 * 最小字符串长度校验类<p></p>
 * 0 < min <= length
 * @author hyl
 */
public class MinStringLengthValidator extends ValidatorBase {
    public static void createValidator(TextInputControl validateControl, String message, int min) {
        new MinStringLengthValidator(validateControl, message, min);
    }

    /**
     * 最小值(包含本身)
     */
    private int min;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param min     最小值(包含本身)
     */
    private MinStringLengthValidator(TextInputControl validateControl, String message, int min) throws IllegalArgumentException {
        if (min <= 0) {
            throw new IllegalArgumentException("最小长度不能小于0");
        }
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
        hasErrors.set(true);
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText();
        if ((text == null) || text.isEmpty()) {
            return;
        }
        int i = text.length();
        if (i < min) {
            return;
        }
        hasErrors.set(false);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MinStringLengthValidator that = (MinStringLengthValidator) o;
        return min == that.min;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min);
    }
}
