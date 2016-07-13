package com.exist.service.impl;

import com.exist.dao.PersonDao;
import com.exist.dto.PersonDto;
import com.exist.model.Person;
import com.exist.model.enums.Sort;
import com.exist.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonServiceImpl extends GenericServiceImpl<PersonDto, Person, Long> implements PersonService{
    @Autowired
    private PersonDao personDao;

    public PersonServiceImpl(){
        super.setDao(personDao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> getAllBy(Sort sort, boolean isAscending){
        return personDao.getAllBy(sort, isAscending)
                        .stream()
                        .map(p -> mapper.map(p, PersonDto.class))
                        .collect(Collectors.toList());
    }
}
