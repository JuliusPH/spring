package com.exist.dao.impl;

import com.exist.dao.PersonDao;
import com.exist.model.Person;
import com.exist.model.enums.Sort;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDaoImpl extends GenericDaoImpl<Person, Long> implements PersonDao{
    public PersonDaoImpl(){}
    
    @Override
    public List<Person> getAllBy(Sort sort, boolean isAscending){
        Session session = getSession();
        Criteria criteria = session.createCriteria(Person.class);
        switch(sort){
            case GWA:
                if(isAscending){
                    criteria.addOrder(Order.asc("gwa"));
                }
                else{
                    criteria.addOrder(Order.desc("gwa"));
                }
                break;
            case DateHired:
                if(isAscending){
                    criteria.addOrder(Order.asc("dateHired"));
                }
                else{
                    criteria.addOrder(Order.desc("dateHired"));
                }
                break;
            case LastName:
                if(isAscending){
                    criteria.addOrder(Order.asc("name.lastName"));
                }
                else{
                    criteria.addOrder(Order.desc("name.lastName"));
                }
                break;
            default:
                if(isAscending){
                    criteria.addOrder(Order.asc("id"));
                }
                else{
                    criteria.addOrder(Order.desc("id"));
                }
                break;
        }
        List<Person> persons = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        session.close();
        return persons;
    }
}
