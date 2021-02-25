package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;

/**
 * 固定长度字符串校验类<p></p>
 *
 * @author hyl
 */
public class FixedStringLengthValidator extends ValidatorBase {
    public static void createValidator(TextInputControl validateControl, String message, int fixedLength) {
        new FixedStringLengthValidator(validateControl, message, fixedLength);
    }

    /**
     * 最小值(包含本身)
     */
    private int fixedLength;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param fixedLength     最小值(包含本身)
     */
    private FixedStringLengthValidator(TextInputControl validateControl, String message, int fixedLength) throws IllegalArgumentException {
        if (fixedLength <= 0) {
            throw new IllegalArgumentException("固定长度不能小于0");
        }
        message = message + ",固定长度=" + fixedLength;
        this.setMessage(message);
        this.fixedLength = fixedLength;
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
        if (i != fixedLength) {
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
        FixedStringLengthValidator that = (FixedStringLengthValidator) o;
        return fixedLength == that.fixedLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fixedLength);
    }
}
