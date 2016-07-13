package com.exist.service.impl;

import com.exist.dao.RoleDao;
import com.exist.dao.impl.GenericDaoImpl;
import com.exist.dto.RoleDto;
import com.exist.model.Role;
import com.exist.service.RoleService;
import com.exist.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends GenericServiceImpl<RoleDto, Role, Long> implements RoleService{
    @Autowired
    private RoleDao roleDao;
    public RoleServiceImpl(){
        super.setDao(roleDao);
    }
}
