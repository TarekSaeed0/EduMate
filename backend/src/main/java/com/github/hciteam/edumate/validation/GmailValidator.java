package com.github.hciteam.edumate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GmailValidator implements ConstraintValidator<Gmail, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		return value.toLowerCase().endsWith("@gmail.com");
	}
}
