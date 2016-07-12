package com.exist.dao.impl;

import com.exist.dao.GenericDao;
import com.exist.dao.command.DaoCommandInvoker;
import com.exist.dao.command.impl.AddDaoCommand;
import com.exist.dao.command.impl.UpdateAllDaoCommand;
import com.exist.dao.command.impl.UpdateDaoCommand;
import com.exist.dao.command.impl.DeleteAllDaoCommand;
import com.exist.dao.command.impl.DeleteDaoCommand;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.FetchMode;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K>{
    private SessionFactory sessionFactory;
    protected Class<? extends E> daoType;
    protected DaoCommandInvoker daoCommandInvoker;
    
    public GenericDaoImpl(SessionFactory sessionFactory){
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        daoType = (Class) parameterizedType.getActualTypeArguments()[0];
        this.sessionFactory = sessionFactory;
    }

    public void setDaoCommandInvoker(DaoCommandInvoker daoCommandInvoker){
        this.daoCommandInvoker = daoCommandInvoker;
    }

    protected Session getSession(){
        return sessionFactory.openSession();
    }
    
    @Override
    public boolean add(E entity){
        System.out.println("added");
        Session session = getSession();
        return daoCommandInvoker.execute(new AddDaoCommand<E>(session, entity), session);
    }
    
    @Override
    public boolean update(E entity){
        Session session = getSession();
        return daoCommandInvoker.execute(new UpdateDaoCommand<E>(session, entity), session);
    }
    
    @Override
    public boolean delete(E entity){
        Session session = getSession();
        return daoCommandInvoker.execute(new DeleteDaoCommand<E>(session, entity), session);
    }
    
    @Override
    public E get(K key){
        Session session = getSession();
        E entity = (E) session.get(daoType, key);
        session.close();
        return entity;
    }
    
    @Override
    public List<E> getAll(){
        Session session = getSession();
        List<E> list = session.createCriteria(daoType)
                                   .addOrder(Order.asc("id"))
                                   .setCacheable(true)
                                   .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                                   .list();
        session.close();
        return list;
    }
    
    @Override
    public boolean deleteAll(Collection<E> entities){
        Session session = getSession();
        return daoCommandInvoker.execute(new DeleteAllDaoCommand<E>(session, entities), session);
    }
    
    @Override
    public boolean updateAll(Collection<E> entities){
        Session session = getSession();
        return daoCommandInvoker.execute(new UpdateAllDaoCommand<E>(session, entities), session);
    }
}
