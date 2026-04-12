package com.familytree.platform.system.domain;

import java.util.Date;
import com.familytree.platform.common.core.domain.BaseEntity;

/**
 * 人员-家庭关联对象 family_person_household
 * 
 * @author familytree
 * @date 2026-04-12
 */
public class FamilyPersonHousehold extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 人员ID */
    private Long personId;

    /** 家庭ID */
    private Long householdId;

    /** 关系类型（如：成员、户主等） */
    private String relationshipType;

    /** 加入日期 */
    private Date joinDate;

    /** 离开日期 */
    private Date leaveDate;

    // 关联对象
    private FamilyPerson person;
    private FamilyHousehold household;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getPersonId()
    {
        return personId;
    }

    public void setPersonId(Long personId)
    {
        this.personId = personId;
    }

    public Long getHouseholdId()
    {
        return householdId;
    }

    public void setHouseholdId(Long householdId)
    {
        this.householdId = householdId;
    }

    public String getRelationshipType()
    {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType)
    {
        this.relationshipType = relationshipType;
    }

    public Date getJoinDate()
    {
        return joinDate;
    }

    public void setJoinDate(Date joinDate)
    {
        this.joinDate = joinDate;
    }

    public Date getLeaveDate()
    {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate)
    {
        this.leaveDate = leaveDate;
    }

    public FamilyPerson getPerson()
    {
        return person;
    }

    public void setPerson(FamilyPerson person)
    {
        this.person = person;
    }

    public FamilyHousehold getHousehold()
    {
        return household;
    }

    public void setHousehold(FamilyHousehold household)
    {
        this.household = household;
    }
}
