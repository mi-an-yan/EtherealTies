package com.familytree.platform.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.familytree.platform.system.mapper.FamilyPersonMapper;
import com.familytree.platform.system.domain.FamilyPerson;
import com.familytree.platform.system.service.IFamilyPersonService;

/**
 * 人员信息Service实现
 * 
 * @author familytree
 * @date 2026-04-12
 */
@Service
public class FamilyPersonServiceImpl implements IFamilyPersonService
{
    @Autowired
    private FamilyPersonMapper familyPersonMapper;

    /**
     * 查询人员信息
     * 
     * @param id 人员信息主键
     * @return 人员信息
     */
    @Override
    public FamilyPerson selectFamilyPersonById(Long id)
    {
        return familyPersonMapper.selectFamilyPersonById(id);
    }

    /**
     * 查询人员信息列表
     * 
     * @param familyPerson 人员信息
     * @return 人员信息集合
     */
    @Override
    public List<FamilyPerson> selectFamilyPersonList(FamilyPerson familyPerson)
    {
        return familyPersonMapper.selectFamilyPersonList(familyPerson);
    }

    /**
     * 新增人员信息
     * 
     * @param familyPerson 人员信息
     * @return 结果
     */
    @Override
    public int insertFamilyPerson(FamilyPerson familyPerson)
    {
        return familyPersonMapper.insertFamilyPerson(familyPerson);
    }

    /**
     * 修改人员信息
     * 
     * @param familyPerson 人员信息
     * @return 结果
     */
    @Override
    public int updateFamilyPerson(FamilyPerson familyPerson)
    {
        return familyPersonMapper.updateFamilyPerson(familyPerson);
    }

    /**
     * 删除人员信息
     * 
     * @param id 人员信息主键
     * @return 结果
     */
    @Override
    public int deleteFamilyPersonById(Long id)
    {
        return familyPersonMapper.deleteFamilyPersonById(id);
    }

    /**
     * 批量删除人员信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteFamilyPersonByIds(Long[] ids)
    {
        return familyPersonMapper.deleteFamilyPersonByIds(ids);
    }

    /**
     * 根据父亲ID查询子女
     * 
     * @param fatherId 父亲ID
     * @return 子女列表
     */
    @Override
    public List<FamilyPerson> selectChildrenByFatherId(Long fatherId)
    {
        return familyPersonMapper.selectChildrenByFatherId(fatherId);
    }

    /**
     * 根据母亲ID查询子女
     * 
     * @param motherId 母亲ID
     * @return 子女列表
     */
    @Override
    public List<FamilyPerson> selectChildrenByMotherId(Long motherId)
    {
        return familyPersonMapper.selectChildrenByMotherId(motherId);
    }

    /**
     * 搜索人员
     * 
     * @param keyword 关键词
     * @return 人员列表
     */
    @Override
    public List<FamilyPerson> searchFamilyPersons(String keyword)
    {
        return familyPersonMapper.searchFamilyPersons(keyword);
    }

    /**
     * 获取人员完整信息（包含关联对象）
     * 
     * @param id 人员ID
     * @return 人员完整信息
     */
    @Override
    public FamilyPerson getFamilyPersonWithRelations(Long id)
    {
        FamilyPerson person = familyPersonMapper.selectFamilyPersonById(id);
        if (person != null) {
            if (person.getFatherId() != null) {
                person.setFather(familyPersonMapper.selectFamilyPersonById(person.getFatherId()));
            }
            if (person.getMotherId() != null) {
                person.setMother(familyPersonMapper.selectFamilyPersonById(person.getMotherId()));
            }
            if (person.getSpouseId() != null) {
                person.setSpouse(familyPersonMapper.selectFamilyPersonById(person.getSpouseId()));
            }
        }
        return person;
    }
}
