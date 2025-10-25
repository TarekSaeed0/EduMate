package com.github.hciteam.edumate.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = GmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Gmail {
	String message() default "Email must be a Gmail address (@gmail.com)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
