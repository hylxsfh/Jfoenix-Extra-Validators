package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.Objects;

/**
 * 必添、必选校验类<p></p>
 * @author hyl
 */
public class RequiredValidator extends ValidatorBase {
    public static void createValidator(TextInputControl validateControl, String message) {
        new RequiredValidator(validateControl, message);
    }
    public static void createValidator(ComboBoxBase validateControl, String message) {
        new RequiredValidator(validateControl, message);
    }

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     */
    private RequiredValidator(Control validateControl, String message) {
        this.setMessage(message);
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
        if (validateControl instanceof TextInputControl) {
            ((TextField)validateControl).setOnAction(actionEvent -> {
                control.validate();
            });
        }
        if (validateControl instanceof ComboBoxBase) {
            ((ComboBoxBase)validateControl).setOnAction(actionEvent -> {
                control.validate();
            });
        }
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        }
        if (srcControl.get() instanceof ComboBoxBase) {
            evalComboBoxField();
        }

    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        if (textField.getText() == null || textField.getText().isEmpty()) {
            hasErrors.set(true);
        } else {
            hasErrors.set(false);
        }
    }

    private void evalComboBoxField() {
        ComboBoxBase comboField = (ComboBoxBase) srcControl.get();
        Object value = comboField.getValue();
        hasErrors.set(value == null || value.toString().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequiredValidator that = (RequiredValidator) o;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
