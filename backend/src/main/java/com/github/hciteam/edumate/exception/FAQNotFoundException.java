package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class FAQNotFoundException extends ApiException {
	public FAQNotFoundException() {
		super("FAQ_NOT_FOUND", "FAQ with this ID was not found",
				HttpStatus.NOT_FOUND);
	}
}
