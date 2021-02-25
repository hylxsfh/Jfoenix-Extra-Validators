package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.validation.base.ValidatorBase;

import java.util.Objects;

/**
 * 晚于另一个JFXDatePicker校验类<p></p>
 *
 * @author hyl
 */
public class FutureOrPresentOtherDatePickerValidator extends ValidatorBase {
    /**
     * @param validateControl
     * @param message
     * @param otherDP 对比的JFXDatePicker，必须比otherDP晚
     */
    public static void createValidator(JFXDatePicker validateControl, String message, JFXDatePicker otherDP) {
        new FutureOrPresentOtherDatePickerValidator(validateControl, message, otherDP);
    }

    private JFXDatePicker otherDP;

    /**
     * @param validateControl 需要校验的控件
     * @param message         错误提示
     * @param otherDP
     */
    private FutureOrPresentOtherDatePickerValidator(JFXDatePicker validateControl, String message, JFXDatePicker otherDP) {
        this.setMessage(message);
        this.otherDP = otherDP;

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
        //选择被比较日期时也触发validate
        this.otherDP.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        validateControl.validate();
                    }
                }
        );
        this.otherDP.setOnAction(actionEvent -> {
            validateControl.validate();
        });
    }

    @Override
    protected void eval() {
        hasErrors.set(true);
        JFXDatePicker dp = (JFXDatePicker) srcControl.get();
        if (dp.getValue() == null) {
            return;
        }
        if (otherDP.getValue() == null) {
            return;
        }
        if (dp.getValue().compareTo(this.otherDP.getValue()) < 0) {
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
        FutureOrPresentOtherDatePickerValidator that = (FutureOrPresentOtherDatePickerValidator) o;
        return otherDP.equals(that.otherDP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(otherDP);
    }
}
