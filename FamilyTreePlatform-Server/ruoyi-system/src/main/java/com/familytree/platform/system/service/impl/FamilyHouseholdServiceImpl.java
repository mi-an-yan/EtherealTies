package com.familytree.platform.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.familytree.platform.system.mapper.FamilyHouseholdMapper;
import com.familytree.platform.system.domain.FamilyHousehold;
import com.familytree.platform.system.domain.FamilyPersonHousehold;
import com.familytree.platform.system.service.IFamilyHouseholdService;
import com.familytree.platform.system.service.IFamilyPersonService;

/**
 * 家庭信息Service实现
 * 
 * @author familytree
 * @date 2026-04-12
 */
@Service
public class FamilyHouseholdServiceImpl implements IFamilyHouseholdService
{
    @Autowired
    private FamilyHouseholdMapper familyHouseholdMapper;

    @Autowired
    private IFamilyPersonService familyPersonService;

    /**
     * 查询家庭信息
     * 
     * @param id 家庭信息主键
     * @return 家庭信息
     */
    @Override
    public FamilyHousehold selectFamilyHouseholdById(Long id)
    {
        return familyHouseholdMapper.selectFamilyHouseholdById(id);
    }

    /**
     * 查询家庭信息列表
     * 
     * @param familyHousehold 家庭信息
     * @return 家庭信息集合
     */
    @Override
    public List<FamilyHousehold> selectFamilyHouseholdList(FamilyHousehold familyHousehold)
    {
        return familyHouseholdMapper.selectFamilyHouseholdList(familyHousehold);
    }

    /**
     * 新增家庭信息
     * 
     * @param familyHousehold 家庭信息
     * @return 结果
     */
    @Override
    public int insertFamilyHousehold(FamilyHousehold familyHousehold)
    {
        return familyHouseholdMapper.insertFamilyHousehold(familyHousehold);
    }

    /**
     * 修改家庭信息
     * 
     * @param familyHousehold 家庭信息
     * @return 结果
     */
    @Override
    public int updateFamilyHousehold(FamilyHousehold familyHousehold)
    {
        return familyHouseholdMapper.updateFamilyHousehold(familyHousehold);
    }

    /**
     * 删除家庭信息
     * 
     * @param id 家庭信息主键
     * @return 结果
     */
    @Override
    public int deleteFamilyHouseholdById(Long id)
    {
        return familyHouseholdMapper.deleteFamilyHouseholdById(id);
    }

    /**
     * 批量删除家庭信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteFamilyHouseholdByIds(Long[] ids)
    {
        return familyHouseholdMapper.deleteFamilyHouseholdByIds(ids);
    }

    /**
     * 查询家庭成员
     * 
     * @param householdId 家庭ID
     * @return 成员列表
     */
    @Override
    public List<FamilyPersonHousehold> selectHouseholdMembers(Long householdId)
    {
        List<FamilyPersonHousehold> members = familyHouseholdMapper.selectHouseholdMembers(householdId);
        for (FamilyPersonHousehold member : members) {
            member.setPerson(familyPersonService.selectFamilyPersonById(member.getPersonId()));
        }
        return members;
    }

    /**
     * 查询人员所属家庭
     * 
     * @param personId 人员ID
     * @return 家庭列表
     */
    @Override
    public List<FamilyPersonHousehold> selectPersonHouseholds(Long personId)
    {
        List<FamilyPersonHousehold> households = familyHouseholdMapper.selectPersonHouseholds(personId);
        for (FamilyPersonHousehold household : households) {
            household.setHousehold(familyHouseholdMapper.selectFamilyHouseholdById(household.getHouseholdId()));
        }
        return households;
    }

    /**
     * 新增人员-家庭关联
     * 
     * @param personHousehold 人员-家庭关联
     * @return 结果
     */
    @Override
    public int insertFamilyPersonHousehold(FamilyPersonHousehold personHousehold)
    {
        return familyHouseholdMapper.insertFamilyPersonHousehold(personHousehold);
    }

    /**
     * 修改人员-家庭关联
     * 
     * @param personHousehold 人员-家庭关联
     * @return 结果
     */
    @Override
    public int updateFamilyPersonHousehold(FamilyPersonHousehold personHousehold)
    {
        return familyHouseholdMapper.updateFamilyPersonHousehold(personHousehold);
    }

    /**
     * 删除人员-家庭关联
     * 
     * @param id 关联ID
     * @return 结果
     */
    @Override
    public int deleteFamilyPersonHouseholdById(Long id)
    {
        return familyHouseholdMapper.deleteFamilyPersonHouseholdById(id);
    }

    /**
     * 批量删除人员-家庭关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteFamilyPersonHouseholdByIds(Long[] ids)
    {
        return familyHouseholdMapper.deleteFamilyPersonHouseholdByIds(ids);
    }

    /**
     * 获取家庭完整信息（包含成员）
     * 
     * @param id 家庭ID
     * @return 家庭完整信息
     */
    @Override
    public FamilyHousehold getFamilyHouseholdWithMembers(Long id)
    {
        FamilyHousehold household = familyHouseholdMapper.selectFamilyHouseholdById(id);
        if (household != null) {
            household.setMembers(selectHouseholdMembers(id));
        }
        return household;
    }
}
