package com.exist.validation;

import com.exist.dto.AddressDto;
import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddressValidator implements Validator{
    @Override
	public boolean supports(Class<?> classObject){
		return classObject == AddressDto.class;
	}

	@Override
	public void validate(Object object, Errors errors){
        AddressDto address = (AddressDto)object;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.streetNumber", "streetnumber.invalid", "Street Number is invalid");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.barangay", "barangay.invalid", "Barangay is invalid");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address.city", "city.invalid", "City is invalid");
        
        if(!GenericValidator.matchRegexp(address.getZipCode(), "^\\d{4}$")){
            errors.rejectValue("address.zipCode", "zipcode.invalid", "Zip Code is invalid");
        }
    }
}
