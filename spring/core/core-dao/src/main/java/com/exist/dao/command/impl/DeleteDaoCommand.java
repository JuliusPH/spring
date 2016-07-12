package com.exist.dao.command.impl;

import com.exist.dao.command.DaoCommand;
import org.hibernate.Session;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class DeleteDaoCommand<E> implements DaoCommand{
    private Session session;
    private E entity;
    
    public DeleteDaoCommand(Session session, E entity){
        this.session = session;
        this.entity = entity;
    }
    
    @Override
    public void execute(){
        System.out.println(entity);
        session.delete(entity);
    }
}