package com.exist.dao.impl;

import com.exist.dao.ContactDao;
import com.exist.model.Contact;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao{
    public ContactDaoImpl(){}
}
