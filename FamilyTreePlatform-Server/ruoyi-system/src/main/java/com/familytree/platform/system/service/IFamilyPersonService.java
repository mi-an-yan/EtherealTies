package com.familytree.platform.system.service;

import java.util.List;
import com.familytree.platform.system.domain.FamilyPerson;

/**
 * 人员信息Service接口
 * 
 * @author familytree
 * @date 2026-04-12
 */
public interface IFamilyPersonService
{
    /**
     * 查询人员信息
     * 
     * @param id 人员信息主键
     * @return 人员信息
     */
    public FamilyPerson selectFamilyPersonById(Long id);

    /**
     * 查询人员信息列表
     * 
     * @param familyPerson 人员信息
     * @return 人员信息集合
     */
    public List<FamilyPerson> selectFamilyPersonList(FamilyPerson familyPerson);

    /**
     * 新增人员信息
     * 
     * @param familyPerson 人员信息
     * @return 结果
     */
    public int insertFamilyPerson(FamilyPerson familyPerson);

    /**
     * 修改人员信息
     * 
     * @param familyPerson 人员信息
     * @return 结果
     */
    public int updateFamilyPerson(FamilyPerson familyPerson);

    /**
     * 删除人员信息
     * 
     * @param id 人员信息主键
     * @return 结果
     */
    public int deleteFamilyPersonById(Long id);

    /**
     * 批量删除人员信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFamilyPersonByIds(Long[] ids);

    /**
     * 根据父亲ID查询子女
     * 
     * @param fatherId 父亲ID
     * @return 子女列表
     */
    public List<FamilyPerson> selectChildrenByFatherId(Long fatherId);

    /**
     * 根据母亲ID查询子女
     * 
     * @param motherId 母亲ID
     * @return 子女列表
     */
    public List<FamilyPerson> selectChildrenByMotherId(Long motherId);

    /**
     * 搜索人员
     * 
     * @param keyword 关键词
     * @return 人员列表
     */
    public List<FamilyPerson> searchFamilyPersons(String keyword);

    /**
     * 获取人员完整信息（包含关联对象）
     * 
     * @param id 人员ID
     * @return 人员完整信息
     */
    public FamilyPerson getFamilyPersonWithRelations(Long id);
}
