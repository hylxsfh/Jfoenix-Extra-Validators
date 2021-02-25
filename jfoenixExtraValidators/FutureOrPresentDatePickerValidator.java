package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.validation.base.ValidatorBase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 将来或当天日期JFXDatePicker校验类<p></p>
 *
 * @author hyl
 */
public class FutureOrPresentDatePickerValidator extends ValidatorBase {
    /**
     * @param validateControl
     * @param message
     * @param date            日期格式yyyy-MM-dd，如2021-01-01
     */
    public static void createValidator(JFXDatePicker validateControl, String message, String date) {
        new FutureOrPresentDatePickerValidator(validateControl, message, date);
    }

    /**
     * 不早于此日期(包含本身)
     */
    private LocalDate date;

    /**
     * @param validateControl 需要校验的控件
     * @param message         错误提示
     * @param date
     */
    private FutureOrPresentDatePickerValidator(JFXDatePicker validateControl, String message, String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        message = message + ",最早日期=" + date;
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
        JFXDatePicker dp = (JFXDatePicker) srcControl.get();
        if (dp.getValue() == null) {
            return;
        }
        if (dp.getValue().compareTo(this.date) < 0) {
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
        FutureOrPresentDatePickerValidator that = (FutureOrPresentDatePickerValidator) o;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
