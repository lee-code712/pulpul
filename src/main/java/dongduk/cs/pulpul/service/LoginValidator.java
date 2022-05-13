package dongduk.cs.pulpul.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import dongduk.cs.pulpul.domain.Member;

@Component
public class LoginValidator implements Validator {
	public boolean supports(Class<?> clazz) {
		return Member.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		Member member = (Member) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "member.id", "ID_REQUIRED", "ID is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "member.password", "PASSWORD_REQUIRED", "Password is required.");
	}
}
