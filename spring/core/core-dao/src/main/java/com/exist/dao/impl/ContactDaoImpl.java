package com.exist.dao.impl;

import com.exist.dao.ContactDao;
import com.exist.model.Contact;
import org.hibernate.SessionFactory;

public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao{
    public ContactDaoImpl(SessionFactory sessionFactory){
         super(sessionFactory);
    }
}
