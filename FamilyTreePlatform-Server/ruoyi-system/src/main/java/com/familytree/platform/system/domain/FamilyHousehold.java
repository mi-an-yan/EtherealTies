package com.familytree.platform.system.domain;

import java.util.Date;
import java.util.List;
import com.familytree.platform.common.core.domain.BaseEntity;

/**
 * 家庭信息对象 family_household
 * 
 * @author familytree
 * @date 2026-04-12
 */
public class FamilyHousehold extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 家庭名称 */
    private String householdName;

    /** 家庭地址 */
    private String address;

    /** 成立日期 */
    private Date foundingDate;

    /** 家庭简介 */
    private String description;

    // 关联对象
    private List<FamilyPersonHousehold> members;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getHouseholdName()
    {
        return householdName;
    }

    public void setHouseholdName(String householdName)
    {
        this.householdName = householdName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getFoundingDate()
    {
        return foundingDate;
    }

    public void setFoundingDate(Date foundingDate)
    {
        this.foundingDate = foundingDate;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<FamilyPersonHousehold> getMembers()
    {
        return members;
    }

    public void setMembers(List<FamilyPersonHousehold> members)
    {
        this.members = members;
    }
}
