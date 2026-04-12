package com.familytree.platform.system.domain;

import java.util.Date;
import com.familytree.platform.common.core.domain.BaseEntity;

/**
 * 族谱-人员关联对象 family_tree_person
 * 
 * @author familytree
 * @date 2026-04-12
 */
public class FamilyTreePerson extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 族谱ID */
    private Long treeId;

    /** 人员ID */
    private Long personId;

    /** 辈分 */
    private Integer generation;

    // 关联对象
    private FamilyPerson person;
    private FamilyTree tree;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getTreeId()
    {
        return treeId;
    }

    public void setTreeId(Long treeId)
    {
        this.treeId = treeId;
    }

    public Long getPersonId()
    {
        return personId;
    }

    public void setPersonId(Long personId)
    {
        this.personId = personId;
    }

    public Integer getGeneration()
    {
        return generation;
    }

    public void setGeneration(Integer generation)
    {
        this.generation = generation;
    }

    public FamilyPerson getPerson()
    {
        return person;
    }

    public void setPerson(FamilyPerson person)
    {
        this.person = person;
    }

    public FamilyTree getTree()
    {
        return tree;
    }

    public void setTree(FamilyTree tree)
    {
        this.tree = tree;
    }
}
