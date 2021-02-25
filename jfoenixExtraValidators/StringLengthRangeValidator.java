package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;

/**
 * 字符串长度校验类<p></p>
 * min <= length <= max
 * @author hyl
 */
public class StringLengthRangeValidator extends ValidatorBase {
    public static void createValidator(TextInputControl validateControl, String message, int min, int max) {
        new StringLengthRangeValidator(validateControl, message, min, max);
    }

    /**
     * 最小值(包含本身)
     */
    private int min;

    /**
     * 最大值(包含本身)
     */
    private int max;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param min     最小值(包含本身)
     * @param max     最大值(包含本身)
     */
    private StringLengthRangeValidator(TextInputControl validateControl, String message, int min, int max) {
        message = message + ",最小值=" + min + ",最大值=" + max;
        this.setMessage(message);
        this.min = min;
        this.max = max;
        setIcon(new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE));

        IFXValidatableControl control = ((IFXValidatableControl) validateControl);
        if (!control.getValidators().contains(this)) {
            control.getValidators().add(this);
        }
        //失去焦点时校验
        validateControl.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        control.validate();
                    }
                }
        );
        //回车时校验
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
        if ((i < min) || (i > max)) {
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
        StringLengthRangeValidator that = (StringLengthRangeValidator) o;
        return min == that.min && max == that.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
