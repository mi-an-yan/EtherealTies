package com.familytree.platform.system.domain;

import java.util.Date;
import java.util.List;
import com.familytree.platform.common.core.domain.BaseEntity;

/**
 * 族谱信息对象 family_tree
 * 
 * @author familytree
 * @date 2026-04-12
 */
public class FamilyTree extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 族谱名称 */
    private String treeName;

    /** 创始人 */
    private String founder;

    /** 创建日期 */
    private Date foundingDate;

    /** 族谱简介 */
    private String description;

    // 关联对象
    private List<FamilyTreePerson> members;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTreeName()
    {
        return treeName;
    }

    public void setTreeName(String treeName)
    {
        this.treeName = treeName;
    }

    public String getFounder()
    {
        return founder;
    }

    public void setFounder(String founder)
    {
        this.founder = founder;
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

    public List<FamilyTreePerson> getMembers()
    {
        return members;
    }

    public void setMembers(List<FamilyTreePerson> members)
    {
        this.members = members;
    }
}
