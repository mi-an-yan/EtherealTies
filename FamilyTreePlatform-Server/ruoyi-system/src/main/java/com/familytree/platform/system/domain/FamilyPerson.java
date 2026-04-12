package com.familytree.platform.system.domain;

import java.util.Date;
import com.familytree.platform.common.core.domain.BaseEntity;

/**
 * 人员信息对象 family_person
 * 
 * @author familytree
 * @date 2026-04-12
 */
public class FamilyPerson extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 姓名 */
    private String name;

    /** 性别（1-男，2-女） */
    private String gender;

    /** 出生日期 */
    private Date birthDate;

    /** 逝世日期 */
    private Date deathDate;

    /** 出生地 */
    private String birthPlace;

    /** 逝世地 */
    private String deathPlace;

    /** 个人简介 */
    private String description;

    /** 父亲ID */
    private Long fatherId;

    /** 母亲ID */
    private Long motherId;

    /** 配偶ID */
    private Long spouseId;

    // 关联对象
    private FamilyPerson father;
    private FamilyPerson mother;
    private FamilyPerson spouse;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Date getDeathDate()
    {
        return deathDate;
    }

    public void setDeathDate(Date deathDate)
    {
        this.deathDate = deathDate;
    }

    public String getBirthPlace()
    {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    public String getDeathPlace()
    {
        return deathPlace;
    }

    public void setDeathPlace(String deathPlace)
    {
        this.deathPlace = deathPlace;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Long getFatherId()
    {
        return fatherId;
    }

    public void setFatherId(Long fatherId)
    {
        this.fatherId = fatherId;
    }

    public Long getMotherId()
    {
        return motherId;
    }

    public void setMotherId(Long motherId)
    {
        this.motherId = motherId;
    }

    public Long getSpouseId()
    {
        return spouseId;
    }

    public void setSpouseId(Long spouseId)
    {
        this.spouseId = spouseId;
    }

    public FamilyPerson getFather()
    {
        return father;
    }

    public void setFather(FamilyPerson father)
    {
        this.father = father;
    }

    public FamilyPerson getMother()
    {
        return mother;
    }

    public void setMother(FamilyPerson mother)
    {
        this.mother = mother;
    }

    public FamilyPerson getSpouse()
    {
        return spouse;
    }

    public void setSpouse(FamilyPerson spouse)
    {
        this.spouse = spouse;
    }
}
