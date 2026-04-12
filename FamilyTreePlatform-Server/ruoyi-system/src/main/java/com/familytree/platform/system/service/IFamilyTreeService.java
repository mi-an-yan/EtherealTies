package com.familytree.platform.system.service;

import java.util.List;
import com.familytree.platform.system.domain.FamilyTree;
import com.familytree.platform.system.domain.FamilyTreePerson;

/**
 * 族谱信息Service接口
 * 
 * @author familytree
 * @date 2026-04-12
 */
public interface IFamilyTreeService
{
    /**
     * 查询族谱信息
     * 
     * @param id 族谱信息主键
     * @return 族谱信息
     */
    public FamilyTree selectFamilyTreeById(Long id);

    /**
     * 查询族谱信息列表
     * 
     * @param familyTree 族谱信息
     * @return 族谱信息集合
     */
    public List<FamilyTree> selectFamilyTreeList(FamilyTree familyTree);

    /**
     * 新增族谱信息
     * 
     * @param familyTree 族谱信息
     * @return 结果
     */
    public int insertFamilyTree(FamilyTree familyTree);

    /**
     * 修改族谱信息
     * 
     * @param familyTree 族谱信息
     * @return 结果
     */
    public int updateFamilyTree(FamilyTree familyTree);

    /**
     * 删除族谱信息
     * 
     * @param id 族谱信息主键
     * @return 结果
     */
    public int deleteFamilyTreeById(Long id);

    /**
     * 批量删除族谱信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFamilyTreeByIds(Long[] ids);

    /**
     * 查询族谱成员
     * 
     * @param treeId 族谱ID
     * @return 成员列表
     */
    public List<FamilyTreePerson> selectTreeMembers(Long treeId);

    /**
     * 查询人员所属族谱
     * 
     * @param personId 人员ID
     * @return 族谱列表
     */
    public List<FamilyTreePerson> selectPersonTrees(Long personId);

    /**
     * 新增族谱-人员关联
     * 
     * @param treePerson 族谱-人员关联
     * @return 结果
     */
    public int insertFamilyTreePerson(FamilyTreePerson treePerson);

    /**
     * 修改族谱-人员关联
     * 
     * @param treePerson 族谱-人员关联
     * @return 结果
     */
    public int updateFamilyTreePerson(FamilyTreePerson treePerson);

    /**
     * 删除族谱-人员关联
     * 
     * @param id 关联ID
     * @return 结果
     */
    public int deleteFamilyTreePersonById(Long id);

    /**
     * 批量删除族谱-人员关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFamilyTreePersonByIds(Long[] ids);

    /**
     * 按辈分查询族谱成员
     * 
     * @param treeId 族谱ID
     * @return 按辈分分组的成员列表
     */
    public List<FamilyTreePerson> selectTreeMembersByGeneration(Long treeId);

    /**
     * 获取族谱完整信息（包含成员）
     * 
     * @param id 族谱ID
     * @return 族谱完整信息
     */
    public FamilyTree getFamilyTreeWithMembers(Long id);

    /**
     * 获取族谱可视化数据
     * 
     * @param treeId 族谱ID
     * @return 可视化数据
     */
    public Object getTreeVisualizationData(Long treeId);
}
