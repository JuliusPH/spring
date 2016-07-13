package com.exist.service.impl;

import com.exist.dao.RoleDao;
import com.exist.dao.impl.GenericDaoImpl;
import com.exist.dto.RoleDto;
import com.exist.model.Role;
import com.exist.service.RoleService;
import com.exist.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends GenericServiceImpl<RoleDto, Role, Long> implements RoleService{
    public RoleServiceImpl(RoleDao roleDao){
        super(roleDao);
    }
}
