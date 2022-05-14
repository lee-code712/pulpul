package dongduk.cs.pulpul.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import dongduk.cs.pulpul.domain.Market;

public class MarketValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Market.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "intro", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "policy", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactableTime", "NotBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precaution", "NotBlank");
	}
}
