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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "intro", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "policy", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactableTime", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precaution", "required");
	}
}
