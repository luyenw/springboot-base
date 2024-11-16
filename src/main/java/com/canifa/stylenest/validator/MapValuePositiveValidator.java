package com.canifa.stylenest.validator;
import com.canifa.stylenest.annotation.ValidMapValuePositive;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class MapValuePositiveValidator implements ConstraintValidator<ValidMapValuePositive, Map<String, Long>> {

    @Override
    public boolean isValid(Map<String, Long> map, ConstraintValidatorContext context) {
        if (map == null) {
            return true;
        }
        return map.values().stream().allMatch(value -> value != null && value > 0);
    }
}
