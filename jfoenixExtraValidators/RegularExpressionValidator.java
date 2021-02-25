package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 正则表达式校验类
 */
public class RegularExpressionValidator extends ValidatorBase {

    public static void createValidator(TextInputControl validateControl, String message, String regex) {
        new RegularExpressionValidator(validateControl, message, regex);
    }

    /**
     * 最小值(包含本身)
     */
    private String regex;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param regex   正则表达式
     */
    private RegularExpressionValidator(TextInputControl validateControl, String message, String regex) {
        this.setMessage(message);
        this.regex = regex;
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
        if (!Pattern.matches(regex, text)) {
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
        RegularExpressionValidator that = (RegularExpressionValidator) o;
        return Objects.equals(regex, that.regex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regex);
    }
}
