package com.exist.validation;

import com.exist.model.Name;
import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class NameValidator implements Validator{
    @Override
	public boolean supports(Class<?> classObject){
		return classObject == Name.class;
	}

	@Override
	public void validate(Object object, Errors errors){
        Name name = (Name)object;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name.firstName", "firstname.invalid", "First Name is invalid");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name.middleName", "middlename.invalid", "Middle Name is invalid");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name.lastName", "lastname.invalid", "Last Name is invalid");
    }
}