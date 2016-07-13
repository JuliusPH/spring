package com.exist.service.impl;

import com.exist.dao.ContactDao;
import com.exist.dao.impl.ContactDaoImpl;
import com.exist.dao.impl.GenericDaoImpl;
import com.exist.dto.ContactDto;
import com.exist.model.Contact;
import com.exist.service.ContactService;
import com.exist.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends GenericServiceImpl<ContactDto, Contact, Long> implements ContactService{
    public ContactServiceImpl(ContactDao contactDao){
        super(contactDao);
    }
}
