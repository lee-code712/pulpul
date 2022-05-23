package dongduk.cs.pulpul.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import dongduk.cs.pulpul.domain.Member;

@Component
public class ChangeMemberInfoValidator implements Validator {
	public boolean supports(Class<?> clazz) {
		return Member.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		Member member = (Member) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birth", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zip", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "required");

		String birth = member.getBirth();
		if (birth.length() != 8 || !isStringInt(birth)) {
			errors.rejectValue("birth", "badPattern");
		}
		String phone = member.getPhone();
		if (!isStringInt(phone)) {
			errors.rejectValue("phone", "badPattern");
		}
	}

	public static boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
