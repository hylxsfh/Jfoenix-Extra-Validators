package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * 以前或当天日期校验类<p></p>
 *
 * @author hyl
 */
public class PastOrPresentDateValidator extends ValidatorBase {
    /**
     *
     * @param validateControl
     * @param message
     * @param date 日期格式yyyy-MM-dd，如2021-01-01
     */
    public static void createValidator(TextInputControl validateControl, String message, String date) {
        new PastOrPresentDateValidator(validateControl, message, date);
    }

    /**
     * 不晚于此日期(包含本身)
     */
    private String date;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param date
     */
    private PastOrPresentDateValidator(TextInputControl validateControl, String message, String date) {
        message = message + ",最晚日期=" + date;
        this.setMessage(message);
        this.date = date;
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
        try {
            LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            setMessage("日期格式错误:" + text);
            return;
        }
        if (CharSequence.compare(text, date) > 0) {
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
        PastOrPresentDateValidator that = (PastOrPresentDateValidator) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
