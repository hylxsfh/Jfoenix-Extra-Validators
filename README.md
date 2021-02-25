# Jfoenix-Extra-Validators
Jfoenix扩展校验

给Jfoenix中的JFXTextField、JFXComboBox、JFXDatePicker、JFXTimePicker增加一些扩展的validator。

# 用法
> JFXTextField中的字符串长度不能小于2，不能大于10

    StringLengthRangeValidator.createValidator(textField, '长度非法', 2, 10);
    
