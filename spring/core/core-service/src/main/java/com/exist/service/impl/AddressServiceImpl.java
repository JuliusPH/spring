package com.exist.service.impl;

import com.exist.dao.AddressDao;
import com.exist.dao.impl.GenericDaoImpl;
import com.exist.dto.AddressDto;
import com.exist.model.Address;
import com.exist.service.AddressService;
import com.exist.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressServiceImpl extends GenericServiceImpl<AddressDto, Address, Long> implements AddressService{
    public AddressServiceImpl(AddressDao addressDao){
        super(addressDao);
    }
}
