package com.exist.validation;

import com.exist.dto.ContactDto;
import com.exist.dto.PersonDto;
import com.exist.dto.RoleDto;
import com.exist.model.enums.Gender;
import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class PersonValidator implements Validator{
    private ContactValidator contactValidator = new ContactValidator();
    
    @Override
	public boolean supports(Class<?> classObject) {
		return classObject == PersonDto.class;
	}

	@Override
	public void validate(Object object, Errors errors) {
		PersonDto person = (PersonDto)object;

        ValidationUtils.invokeValidator(new NameValidator(), person.getName(), errors);

        ValidationUtils.invokeValidator(new AddressValidator(), person.getAddress(), errors);
        
        if(validateDate(person.getBirthday())){
            errors.rejectValue("birthday", "birthday.invalid", "Birthday is invalid");
        }
        
        if(validateGwa(person.getGwa())){
            errors.rejectValue("gwa", "gwa.invalid", "GWA is invalid");
        }
        
        if(validateDate(person.getDateHired())){
            errors.rejectValue("dateHired", "dateHired.invalid", "Date hired is invalid");
        }
        
        if(validateContacts(person.getContacts(), errors)){
            errors.rejectValue("contacts", "contacts.invalid", "Contacts must have at least one");
        }
        
        if(validateRoles(person.getRoles())){
            errors.rejectValue("roles", "roles.invalid", "Roles must have at least one");
        }
	}
    
    private boolean validateDate(Date date){
        if(date == null){
            return false;
        }
        
        Date currentDate = Calendar.getInstance().getTime();
        
        return currentDate.after(date);
    }
    
    private boolean validateGwa(float gwa){
        float minGwa = 50f;
        float maxGwa = 100f;
        
        return GenericValidator.isInRange(gwa, minGwa, maxGwa);
    }
    
    private boolean validateContacts(Set<ContactDto> contacts, Errors errors){
        for(ContactDto contact : contacts){
            ValidationUtils.invokeValidator(new ContactValidator(), contact, errors);
        }

        return contacts.size() > 0;
    }
    
    private boolean validateGender(Gender gender){
        if(gender == null){
            return false;
        }
        
        return gender == Gender.Male || gender == Gender.Female;
    }
    
    private boolean validateRoles(Set<RoleDto> roles){
        return roles.size() > 0;
    }
}