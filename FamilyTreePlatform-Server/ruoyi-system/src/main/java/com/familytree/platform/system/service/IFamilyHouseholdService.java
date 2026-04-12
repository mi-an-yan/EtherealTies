package com.familytree.platform.system.service;

import java.util.List;
import com.familytree.platform.system.domain.FamilyHousehold;
import com.familytree.platform.system.domain.FamilyPersonHousehold;

/**
 * 家庭信息Service接口
 * 
 * @author familytree
 * @date 2026-04-12
 */
public interface IFamilyHouseholdService
{
    /**
     * 查询家庭信息
     * 
     * @param id 家庭信息主键
     * @return 家庭信息
     */
    public FamilyHousehold selectFamilyHouseholdById(Long id);

    /**
     * 查询家庭信息列表
     * 
     * @param familyHousehold 家庭信息
     * @return 家庭信息集合
     */
    public List<FamilyHousehold> selectFamilyHouseholdList(FamilyHousehold familyHousehold);

    /**
     * 新增家庭信息
     * 
     * @param familyHousehold 家庭信息
     * @return 结果
     */
    public int insertFamilyHousehold(FamilyHousehold familyHousehold);

    /**
     * 修改家庭信息
     * 
     * @param familyHousehold 家庭信息
     * @return 结果
     */
    public int updateFamilyHousehold(FamilyHousehold familyHousehold);

    /**
     * 删除家庭信息
     * 
     * @param id 家庭信息主键
     * @return 结果
     */
    public int deleteFamilyHouseholdById(Long id);

    /**
     * 批量删除家庭信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFamilyHouseholdByIds(Long[] ids);

    /**
     * 查询家庭成员
     * 
     * @param householdId 家庭ID
     * @return 成员列表
     */
    public List<FamilyPersonHousehold> selectHouseholdMembers(Long householdId);

    /**
     * 查询人员所属家庭
     * 
     * @param personId 人员ID
     * @return 家庭列表
     */
    public List<FamilyPersonHousehold> selectPersonHouseholds(Long personId);

    /**
     * 新增人员-家庭关联
     * 
     * @param personHousehold 人员-家庭关联
     * @return 结果
     */
    public int insertFamilyPersonHousehold(FamilyPersonHousehold personHousehold);

    /**
     * 修改人员-家庭关联
     * 
     * @param personHousehold 人员-家庭关联
     * @return 结果
     */
    public int updateFamilyPersonHousehold(FamilyPersonHousehold personHousehold);

    /**
     * 删除人员-家庭关联
     * 
     * @param id 关联ID
     * @return 结果
     */
    public int deleteFamilyPersonHouseholdById(Long id);

    /**
     * 批量删除人员-家庭关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFamilyPersonHouseholdByIds(Long[] ids);

    /**
     * 获取家庭完整信息（包含成员）
     * 
     * @param id 家庭ID
     * @return 家庭完整信息
     */
    public FamilyHousehold getFamilyHouseholdWithMembers(Long id);
}
