package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import java.util.Objects;

/**
 * 整数范围校验类<p></p>
 * min <= x <= max <p></p>
 * 使用方法: new IntRangeValidator(需要校验的JFXTextField, "数值范围非法", 0, 9);
 *
 * @author hyl
 */
public class IntRangeValidator extends ValidatorBase {
    public static void createValidator(TextInputControl validateControl, String message, int min, int max) {
        new IntRangeValidator(validateControl, message, min, max);
    }

    private int min;

    private int max;

    /**
     * @param validateControl 需要校验的控件
     * @param message         错误提示
     * @param min
     * @param max
     */
    private IntRangeValidator(TextInputControl validateControl, String message, int min, int max) {
        message = message + ",最小值=" + min + ",最大值=" + max;
        this.setMessage(message);
        this.min = min;
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
            if ((i < min) || (i > max)) {
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
        IntRangeValidator that = (IntRangeValidator) o;
        return min == that.min && max == that.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
