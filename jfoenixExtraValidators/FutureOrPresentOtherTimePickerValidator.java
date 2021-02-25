package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.base.ValidatorBase;

import java.util.Objects;

/**
 * 晚于另一个JFXTimePicker校验类<p></p>
 *
 * @author hyl
 */
public class FutureOrPresentOtherTimePickerValidator extends ValidatorBase {
    /**
     * @param validateControl
     * @param message
     * @param otherTP 对比的JFXTimePicker，必须比otherDP晚
     */
    public static void createValidator(JFXTimePicker validateControl, String message, JFXTimePicker otherTP) {
        new FutureOrPresentOtherTimePickerValidator(validateControl, message, otherTP);
    }

    private JFXTimePicker otherTP;

    /**
     * @param validateControl 需要校验的控件
     * @param message         错误提示
     * @param otherTP
     */
    private FutureOrPresentOtherTimePickerValidator(JFXTimePicker validateControl, String message, JFXTimePicker otherTP) {
        this.setMessage(message);
        this.otherTP = otherTP;

        if (!validateControl.getValidators().contains(this)) {
            validateControl.getValidators().add(this);
        }
        validateControl.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        validateControl.validate();
                    }
                }
        );
        validateControl.setOnAction(actionEvent -> {
            validateControl.validate();
        });
        //选择被比较时间时也触发validate
        this.otherTP.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        validateControl.validate();
                    }
                }
        );
        this.otherTP.setOnAction(actionEvent -> {
            validateControl.validate();
        });
    }

    @Override
    protected void eval() {
        hasErrors.set(true);
        JFXTimePicker tp = (JFXTimePicker) srcControl.get();
        if (tp.getValue() == null) {
            return;
        }
        if (otherTP.getValue() == null) {
            return;
        }
        if (tp.getValue().compareTo(this.otherTP.getValue()) < 0) {
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
        FutureOrPresentOtherTimePickerValidator that = (FutureOrPresentOtherTimePickerValidator) o;
        return otherTP.equals(that.otherTP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(otherTP);
    }
}
