package cn.stronger.we.commons.validator;

import cn.stronger.we.commons.framework.ResultErrCodeI;
import cn.stronger.we.commons.utils.StringTools;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 自定义非空注解验证类
 * @department Platform Center
 * @date 2024-09-07 16:19
 */
public class CusNotEmptyValidator implements ConstraintValidator<CusNotEmpty, Object> {
    private String message;

    @Override
    public void initialize(CusNotEmpty notEmpty) {
        this.message = notEmpty.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (null == value) {
            handle(context);
            return false;
        }

        if (value instanceof String
                && StringTools.isNotEmpty(String.valueOf(value))) {
            return true;
        }

        if (value instanceof List
                && !CollectionUtils.isEmpty((List<?>) value)) {
            return true;
        }

        if (value instanceof Set
                && !CollectionUtils.isEmpty((Set<?>) value)) {
            return true;
        }

        if (value instanceof Integer) {
            return true;
        }

        if (value instanceof Long) {
            return true;
        }

        if (value instanceof Boolean) {
            return true;
        }

        handle(context);
        return false;
    }

    private void handle(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(ResultErrCodeI.NOT_EMPTY_CODE).addConstraintViolation()
                .buildConstraintViolationWithTemplate(MessageFormat.format(ResultErrCodeI.NOT_EMPTY_MSG, this.message)).addConstraintViolation();
    }

}
