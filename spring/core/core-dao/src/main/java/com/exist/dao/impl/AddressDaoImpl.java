package com.exist.dao.impl;

import com.exist.dao.AddressDao;
import com.exist.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl extends GenericDaoImpl<Address, Long> implements AddressDao{
    public AddressDaoImpl(){}
}
