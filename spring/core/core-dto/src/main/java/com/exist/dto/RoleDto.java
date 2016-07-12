package com.exist.dto;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class RoleDto extends BaseEntityDto{
    private String value;
    
    public RoleDto(){}
    
    public RoleDto(String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
    @Override
    public int hashCode(){
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(value);
        return hcb.toHashCode();
    }
 
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof RoleDto)){
            return false;
        }
        RoleDto otherRole = (RoleDto) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(value, otherRole.getValue());
        return eb.isEquals();
    }
}
