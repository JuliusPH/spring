package com.exist.service.impl;

import com.exist.dao.AddressDao;
import com.exist.dao.impl.GenericDaoImpl;
import com.exist.dto.AddressDto;
import com.exist.model.Address;
import com.exist.service.AddressService;
import com.exist.service.impl.GenericServiceImpl;

public class AddressServiceImpl extends GenericServiceImpl<AddressDto, Address, Long> implements AddressService{
    public AddressServiceImpl(AddressDao addressDao){
        super(addressDao);
    }
}
