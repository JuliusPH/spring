package com.exist.dao.impl;

import com.exist.dao.RoleDao;
import com.exist.model.Role;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role, Long> implements RoleDao{
    public RoleDaoImpl(SessionFactory sessionFactory){
         super(sessionFactory);
    }
}
