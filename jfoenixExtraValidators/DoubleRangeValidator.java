package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;

/**
 * 浮点数范围校验类<p></p>
 * min <= length <= max
 */
public class DoubleRangeValidator extends ValidatorBase {
    public static void createValidator(TextInputControl validateControl, String message, double min, double max) {
        new DoubleRangeValidator(validateControl, message, min, max);
    }

    /**
     * 最小值(包含本身)
     */
    private double min;

    /**
     * 最大值(包含本身)
     */
    private double max;

    /**
     * @param validateControl 需要校验的控件
     * @param message         错误提示
     * @param min             最小值(包含本身)
     * @param max             最大值(包含本身)
     */
    private DoubleRangeValidator(TextInputControl validateControl, String message, double min, double max) {
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
            double i = Double.parseDouble(text);
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
        DoubleRangeValidator that = (DoubleRangeValidator) o;
        return Double.compare(that.min, min) == 0 && Double.compare(that.max, max) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}
