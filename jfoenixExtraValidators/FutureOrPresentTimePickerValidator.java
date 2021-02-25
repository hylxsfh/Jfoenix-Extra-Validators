package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.base.ValidatorBase;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 将来或当天时间JFXTimePicker校验类<p></p>
 *
 * @author hyl
 */
public class FutureOrPresentTimePickerValidator extends ValidatorBase {
    /**
     * @param validateControl
     * @param message
     * @param time            时间格式HH:mm，如10:20
     */
    public static void createValidator(JFXTimePicker validateControl, String message, String time) {
        new FutureOrPresentTimePickerValidator(validateControl, message, time);
    }

    /**
     * 不早于此时间(包含本身)
     */
    private LocalTime time;

    /**
     * @param validateControl 需要校验的控件
     * @param message         错误提示
     * @param date
     */
    private FutureOrPresentTimePickerValidator(JFXTimePicker validateControl, String message, String date) {
        this.time = LocalTime.parse(date, DateTimeFormatter.ofPattern("HH:mm"));
        message = message + ",最早时间=" + date;
        this.setMessage(message);

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
    }

    @Override
    protected void eval() {
        hasErrors.set(true);
        JFXTimePicker tp = (JFXTimePicker) srcControl.get();
        if (tp.getValue() == null) {
            return;
        }
        if (tp.getValue().compareTo(this.time) < 0) {
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
        FutureOrPresentTimePickerValidator that = (FutureOrPresentTimePickerValidator) o;
        return time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
