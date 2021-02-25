package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;

/**
 * 最大字符串长度校验类<p></p>
 * 0 < length <= max
 * @author hyl
 */
public class MaxStringLengthValidator extends ValidatorBase {
    public static void createValidator(TextInputControl validateControl, String message, int min) {
        new MaxStringLengthValidator(validateControl, message, min);
    }

    /**
     * 最小值(包含本身)
     */
    private int max;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param max     最小值(包含本身)
     */
    private MaxStringLengthValidator(TextInputControl validateControl, String message, int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("最大长度必须大于0");
        }

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
        hasErrors.set(true);
        TextInputControl textField = (TextInputControl) srcControl.get();
        String text = textField.getText();
        int i = 0;
        if (text != null) {
            i = text.length();
        }
        if (i > max) {
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
        MaxStringLengthValidator that = (MaxStringLengthValidator) o;
        return max == that.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(max);
    }
}
