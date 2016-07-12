package com.exist.validation;

import com.exist.dto.ContactDto;
import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContactValidator implements Validator{
    @Override
	public boolean supports(Class<?> classObject){
		return classObject == ContactDto.class;
	}

	@Override
	public void validate(Object object, Errors errors){
        ContactDto contact = (ContactDto)object;

        switch(contact.getContactType()){
            case Email:
                if(!GenericValidator.isEmail(contact.getValue())){
                    errors.rejectValue("value", "email.invalid", "Email is invalid");
                }
                break;
            case Mobile:
                if(!GenericValidator.matchRegexp(contact.getValue(), "^(09|\\+639)\\d{9}$")){
                    errors.rejectValue("value", "mobile.invalid", "Mobile is invalid");
                }
                break;
            case Landline:
                if(!GenericValidator.matchRegexp(contact.getValue(), "^\\d{7}$")){
                    errors.rejectValue("value", "landline.invalid", "Landline is invalid");
                }
                break;
        }
    }
}