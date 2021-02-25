package com.poscard.tcpstation.jfoenixExtraValidators;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 日期时间格式校验类<p></p>
 *
 */
public class DateTimeValidator extends ValidatorBase {

    public static void createValidator(TextInputControl validateControl, String message, FormatStyles formatStyles) {
        new DateTimeValidator(validateControl, message, formatStyles);
    }

    private FormatStyles formatStyles;

    /**
     * @param validateControl 需要校验的控件
     * @param message 错误提示
     * @param formatStyles 格式化样式
     */
    private DateTimeValidator(TextInputControl validateControl, String message, FormatStyles formatStyles) {
        this.setMessage(message);
        this.formatStyles = formatStyles;
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
            if (formatStyles.equals(FormatStyles.yyyy_MM_dd)) {
                LocalDate dt = LocalDate.parse(text, formatStyles.formatter);
            } else if (formatStyles.equals(FormatStyles.HH_mm_ss)) {
                LocalTime dt = LocalTime.parse(text, formatStyles.formatter);
            } else if (formatStyles.equals(FormatStyles.yyyy_MM_dd_HH_mm_ss)) {
                LocalDateTime dt = LocalDateTime.parse(text, formatStyles.formatter);
            } else {
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
        DateTimeValidator that = (DateTimeValidator) o;
        return formatStyles.equals(that.formatStyles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formatStyles);
    }

    /**
     * 日期时间格式化样式类
     */
    public static enum FormatStyles {
        /**
         * 3种日期时间格式
         */
        yyyy_MM_dd(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        HH_mm_ss(DateTimeFormatter.ofPattern("HH:mm:ss")),
        yyyy_MM_dd_HH_mm_ss(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        private DateTimeFormatter formatter;

        FormatStyles(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }
    }

}
