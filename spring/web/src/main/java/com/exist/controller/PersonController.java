package com.exist.controller;

import com.exist.dto.AddressDto;
import com.exist.dto.ContactDto;
import com.exist.dto.PersonDto;
import com.exist.dto.RoleDto;
import com.exist.model.Contact;
import com.exist.model.Name;
import com.exist.model.enums.ContactType;
import com.exist.model.enums.Gender;
import com.exist.model.enums.Sort;
import com.exist.service.ContactService;
import com.exist.service.PersonService;
import com.exist.service.RoleService;
import com.exist.validation.ContactValidator;
import com.exist.validation.PersonValidator;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PersonController{
    @Autowired
	private PersonService personService;
    @Autowired
    private ContactService contactService;
    @Autowired
	private RoleService roleService;

    @RequestMapping(value="/", method= RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "sort", required = false) String sort,
                             @RequestParam(value = "order", required = false) String order){
        sort = sort != null ? sort : "id";
        order = order != null ? order : "ascending";
        boolean isAscending = order.equals("ascending") || order.equals("") ? true : false;
		List<PersonDto> persons;
        switch(sort){
            case "gwa":
                persons = personService.getAllBy(Sort.GWA, isAscending);
                break;
            case "date hired":
                persons = personService.getAllBy(Sort.DateHired, isAscending);
                break;
            case "last name":
                persons = personService.getAllBy(Sort.LastName, isAscending);
                break;
            default:
                persons = personService.getAllBy(Sort.ID, isAscending);
                break;
        }
		ModelAndView model = new ModelAndView("index");
		model.addObject("persons", persons);
		return model;
	}

    @RequestMapping(value="/person/{id}", method= RequestMethod.GET)
    public ModelAndView loadPerson(@PathVariable String id){
        PersonDto person = getPerson(id);
		ModelAndView model = new ModelAndView("person");
        model.addObject("loadPerson", true);
        if(person == null){
            model.addObject("notFound", true);
        }
        else{
            model.addObject("person", person);
        }
		return model;
    }

    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView setupAdd(){
        ModelAndView model = new ModelAndView("addperson");
        PersonDto person = new PersonDto();
        List<RoleDto> roles = roleService.getAll();
        model.addObject("person", person);
		model.addObject("roles", roles);
		model.addObject("addPerson", true);
		return model;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ModelAndView processAdd(@Valid PersonDto person, BindingResult result){
        ModelAndView model = new ModelAndView("index");
        model.addObject("title", "Failed");
        model.addObject("message", "Failed to add person, please try again");
        model.addObject("persons", personService.getAllBy(Sort.ID, true));

        if(result.hasErrors()){
            model.addObject("errors", result.getAllErrors());
        }
        else{
            if(personService.add(person)){
                model.addObject("title", "Success");
                model.addObject("message", "Successfully added " + person.getFullName());
            }
        }
        return model;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public ModelAndView setupUpdate(@PathVariable String id){
        ModelAndView model = new ModelAndView("updateperson");
        PersonDto person = getPerson(id);
        if(person == null){
            model.addObject("notFound", true);
        }
        else{
            model.addObject("person", person);
        }
        List<RoleDto> roles = roleService.getAll();
        model.addObject("roles", roles);
        model.addObject("updatePerson", true);
        return model;
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public ModelAndView processUpdate(@Valid PersonDto person, BindingResult result){
        ModelAndView model = new ModelAndView("index");
        model.addObject("title", "Failed");
        model.addObject("message", "Failed to update, please try again");
        model.addObject("persons", personService.getAllBy(Sort.ID, true));

        if(result.hasErrors()){
            model.addObject("errors", result.getAllErrors());
        }
        else{
            if(personService.update(person)){
                model.addObject("title", "Success");
                model.addObject("message", "Successfully updated " + person.getFullName());
            }
        }
        return model;
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView setupDelete(@PathVariable String id){
        ModelAndView model = new ModelAndView("deleteperson");
        PersonDto person = getPerson(id);
        if(person == null){
            model.addObject("notFound", true);
        }
        else{
            model.addObject("person", person);
        }
        model.addObject("deletePerson", true);
		return model;
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
    public ModelAndView processDelete(@PathVariable String id){
        PersonDto person = getPerson(id);
        ModelAndView model = new ModelAndView("index");
        model.addObject("title", "Failed");
        model.addObject("message", "Failed to delete, please try again");
        model.addObject("persons", personService.getAllBy(Sort.ID, true));
        if(personService.delete(person)){
            model.addObject("title", "Success");
            model.addObject("message", "Successfully deleted " + person.getFullName());
        }
        return model;
    }

    @RequestMapping(value="/update_contacts/{id}", method=RequestMethod.GET)
    public ModelAndView setupUpdateContacts(@PathVariable String id){
        ModelAndView model = new ModelAndView("contacts");
        PersonDto person = getPerson(id);
        if(person == null){
            model.addObject("notFound", true);
        }
        else{
            model.addObject("person", person);
        }
        model.addObject("updateContacts", true);
        return model;
    }

    /*@RequestMapping(value="/update_contacts", method=RequestMethod.POST)
    public ModelAndView processUpdateContacts(@Valid PersonDto person, BindingResult result){
        PersonDto person = getPerson(id);
        ModelAndView model = new ModelAndView("index");
        model.addObject("title", "Failed");
        model.addObject("message", "Failed to update contacts, please try again");

        String email = "";
        String emailId = "";
        if(request.getParameter("email") != null){
            email = request.getParameter("email");
            emailId = request.getParameter("emailid");
        }

        String mobileNumber = "";
        String mobileNumberId = "";
        if(request.getParameter("mobilenumber") != null){
            mobileNumber = request.getParameter("mobilenumber");
            mobileNumberId = request.getParameter("mobilenumberid");
        }

        String landlineNumber = "";
        String landlineNumberId = "";
        if(request.getParameter("landlinenumber") != null){
            landlineNumber = request.getParameter("landlinenumber");
            landlineNumberId = request.getParameter("landlinenumberid");
        }

        String[] deleteContactToggles = request.getParameterValues("deletecontact") != null ?
            request.getParameterValues("deletecontact") : new String[]{};


        Set<ContactDto> toUpdateContacts = new HashSet<ContactDto>();
        Set<ContactDto> toDeleteContacts = new HashSet<ContactDto>();
        if(!email.equals("")){
            ContactDto emailContact = contactService.get(Long.parseLong(emailId));
            if(Arrays.asList(deleteContactToggles).contains("email")){
                toDeleteContacts.add(emailContact);
            }
            else{
                emailContact.setValue(email);
                toUpdateContacts.add(emailContact);
            }
            if(!isValid(emailContact, "email")){
                return model;
            }
        }

        if(!mobileNumber.equals("")){
            ContactDto mobileContact = contactService.get(Long.parseLong(mobileNumberId));
            if(Arrays.asList(deleteContactToggles).contains("mobile")){
                toDeleteContacts.add(mobileContact);
            }
            else{
                mobileContact.setValue(mobileNumber);
                toUpdateContacts.add(mobileContact);
            }
            if(!isValid(mobileContact, "mobile")){
                return model;
            }
        }

        if(!landlineNumber.equals("")){
            ContactDto landlineContact = contactService.get(Long.parseLong(landlineNumberId));
            if(Arrays.asList(deleteContactToggles).contains("landline")){
                toDeleteContacts.add(landlineContact);
            }
            else{
                landlineContact.setValue(landlineNumber);
                toUpdateContacts.add(landlineContact);
            }
            if(!isValid(landlineContact, "landline")){
                return model;
            }
        }

        if(contactService.updateAll(toUpdateContacts) && contactService.deleteAll(toDeleteContacts)){
            model.addObject("title", "Success");
            model.addObject("message", "Successfully updated contacts of " + person.getFullName());
        }
        model.addObject("persons", personService.getAllBy(Sort.ID, true));
        return model;
    }*/
    
    public PersonDto getPerson(String id){
        if(id == null || "".equals(id) || !GenericValidator.isLong(id)){
            return null;
        }
        return personService.get(Long.parseLong(id));
    }

    public PersonDto mapPerson(PersonDto person, HttpServletRequest request) throws IllegalArgumentException, ParseException{
        String firstName = request.getParameter("firstname");
        String middleName = request.getParameter("middlename");
        String lastName = request.getParameter("lastname");
        String birthMonth = request.getParameter("birthmonth");
        String birthDay = request.getParameter("birthday");
        String birthYear = request.getParameter("birthyear");
        String birthDate = birthMonth + "/" + birthDay + "/" + birthYear;
        String gender = request.getParameter("gender");
        String gwa = request.getParameter("gwa");
        String streetNumber = request.getParameter("streetnumber");
        String barangay = request.getParameter("barangay");
        String city = request.getParameter("city");
        String zipCode = request.getParameter("zipcode");
        String isEmployed = request.getParameter("isemployed");
        String hiredMonth = request.getParameter("hiredmonth").equals("") ? "01" : request.getParameter("hiredmonth");
        String hiredDay = request.getParameter("hiredday").equals("") ? "01" : request.getParameter("hiredday");
        String hiredYear = request.getParameter("hiredyear").equals("") ? "1900" : request.getParameter("hiredyear");
        String hiredDate = hiredMonth + "/" + hiredDay + "/" + hiredYear;

        String email = "";
        String emailId = "";
        if(!request.getParameter("email").equals("")){
            email = request.getParameter("email");
        }
        if(!(request.getParameter("emailid") == null && "".equals(request.getParameter("emailid")))){
            emailId = request.getParameter("emailid");
        }

        String mobileNumber = "";
        String mobileNumberId = "";
        if(!request.getParameter("mobilenumber").equals("")){
            mobileNumber = request.getParameter("mobilenumber");
        }
        if(!(request.getParameter("mobilenumberid") == null && "".equals(request.getParameter("mobilenumberid")))){
            mobileNumberId = request.getParameter("mobilenumberid");
        }

        String landlineNumber = "";
        String landlineNumberId = "";
        if(!request.getParameter("landlinenumber").equals("")){
            landlineNumber = request.getParameter("landlinenumber");
        }
        if(!(request.getParameter("landlinenumberid") == null && "".equals(request.getParameter("landlinenumberid")))){
            landlineNumberId = request.getParameter("landlinenumberid");
        }

        String[] roles = request.getParameterValues("roles");

        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        person.setName(new Name(firstName, middleName, lastName));
        person.setAddress(new AddressDto(streetNumber, barangay, city, zipCode));
        person.setBirthday(simpleDateFormat.parse(birthDate));
        person.setGender(gender.equals("male") ? Gender.Male : Gender.Female);
        person.setGwa(Float.parseFloat(gwa));
        person.setEmployed(isEmployed.equals("true") ? true : false);
        if(isEmployed.equals("true")){
            person.setDateHired(simpleDateFormat.parse(hiredDate));
        }
        Set<ContactDto> contactSet = new HashSet<ContactDto>();
        if(!email.equals("")){
            ContactDto emailContact = null;
            if(emailId != null){
                if(!emailId.equals("")){
                    emailContact = contactService.get(Long.parseLong(emailId));
                    emailContact.setValue(email);
                }
            }
            else{
                emailContact = new ContactDto(ContactType.Email, email);
                emailContact.setPerson(person);
            }
            contactSet.add(emailContact);
        }
        else{
            if(emailId != null){
                if(!emailId.equals("")){
                    ContactDto emailContact = contactService.get(Long.parseLong(emailId));
                    contactService.delete(emailContact);
                }
            }
        }

        if(!mobileNumber.equals("")){
            ContactDto mobileContact = null;
            if(mobileNumberId != null) {
                if(!mobileNumberId.equals("")){
                    mobileContact = contactService.get(Long.parseLong(mobileNumberId));
                    mobileContact.setValue(mobileNumber);
                }
            }
            else{
                mobileContact = new ContactDto(ContactType.Mobile, mobileNumber);
                mobileContact.setPerson(person);
            }
            contactSet.add(mobileContact);
        }
        else{
            if(mobileNumberId != null) {
                if(!mobileNumberId.equals("")){
                    ContactDto mobileContact = contactService.get(Long.parseLong(mobileNumberId));
                    contactService.delete(mobileContact);
                }
            }
        }

        if(!landlineNumber.equals("")){
            ContactDto landlineContact = null;
            if(landlineNumberId != null) {
                if(!landlineNumberId.equals("")){
                    landlineContact = contactService.get(Long.parseLong(landlineNumberId));
                    landlineContact.setValue(landlineNumber);
                }
            }
            else{
                landlineContact = new ContactDto(ContactType.Landline, landlineNumber);
                landlineContact.setPerson(person);
            }
            contactSet.add(landlineContact);
        }
        else{
            if(landlineNumberId != null) {
                if(!landlineNumberId.equals("")){
                    ContactDto landlineContact = contactService.get(Long.parseLong(landlineNumberId));
                    contactService.delete(landlineContact);
                }
            }
        }
        person.setContacts(contactSet);
        Set<RoleDto> roleSet = new HashSet<RoleDto>();
        for(String role : roles){
            roleSet.add(roleService.get(Long.parseLong(role)));
        }
        person.setRoles(roleSet);

        return person;
    }

    private boolean isValid(Object target, String objectName){
        BindingResult result = new BeanPropertyBindingResult(target, objectName);
        Validator validator = null;
        if(target instanceof PersonDto){
            validator = new PersonValidator();
        }
        else if(target instanceof ContactDto){
            validator = new ContactValidator();
        }
        ValidationUtils.invokeValidator(validator, target, result);
        return !result.hasErrors();
    }
}
