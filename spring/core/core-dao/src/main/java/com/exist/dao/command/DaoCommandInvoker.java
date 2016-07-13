package com.exist.dao.command;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

@Component
public class DaoCommandInvoker{
    public boolean execute(DaoCommand command, Session session){
        boolean isSuccessful = true;
        Transaction transaction = session.beginTransaction();
        try{
            command.execute();
            transaction.commit();
        }
        catch(HibernateException ex){
            isSuccessful = false;
            transaction.rollback();
            ex.printStackTrace();
        }
        finally{
            session.close();
        }
        return isSuccessful;
    }
}