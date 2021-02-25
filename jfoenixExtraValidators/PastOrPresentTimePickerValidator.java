package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.base.ValidatorBase;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 以前或当天时间JFXTimePicker校验类<p></p>
 *
 * @author hyl
 */
public class PastOrPresentTimePickerValidator extends ValidatorBase {
    /**
     * @param validateControl
     * @param message
     * @param time            时间格式HH:mm，如10:20
     */
    public static void createValidator(JFXTimePicker validateControl, String message, String time) {
        new PastOrPresentTimePickerValidator(validateControl, message, time);
    }

    /**
     * 不晚于此时间(包含本身)
     */
    private LocalTime time;

    /**
     * @param validateControl 需要校验的控件
     * @param message         错误提示
     * @param time
     */
    private PastOrPresentTimePickerValidator(JFXTimePicker validateControl, String message, String time) {
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        message = message + ",最晚时间=" + time;
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
        System.out.println(tp.getValue());
        System.out.println(this.time);
        if (tp.getValue().compareTo(this.time) > 0) {
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
        PastOrPresentTimePickerValidator that = (PastOrPresentTimePickerValidator) o;
        return time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
