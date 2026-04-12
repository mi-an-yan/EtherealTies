package com.familytree.platform.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.familytree.platform.system.mapper.FamilyTreeMapper;
import com.familytree.platform.system.domain.FamilyTree;
import com.familytree.platform.system.domain.FamilyTreePerson;
import com.familytree.platform.system.service.IFamilyTreeService;
import com.familytree.platform.system.service.IFamilyPersonService;

/**
 * 族谱信息Service实现
 * 
 * @author familytree
 * @date 2026-04-12
 */
@Service
public class FamilyTreeServiceImpl implements IFamilyTreeService
{
    @Autowired
    private FamilyTreeMapper familyTreeMapper;

    @Autowired
    private IFamilyPersonService familyPersonService;

    /**
     * 查询族谱信息
     * 
     * @param id 族谱信息主键
     * @return 族谱信息
     */
    @Override
    public FamilyTree selectFamilyTreeById(Long id)
    {
        return familyTreeMapper.selectFamilyTreeById(id);
    }

    /**
     * 查询族谱信息列表
     * 
     * @param familyTree 族谱信息
     * @return 族谱信息集合
     */
    @Override
    public List<FamilyTree> selectFamilyTreeList(FamilyTree familyTree)
    {
        return familyTreeMapper.selectFamilyTreeList(familyTree);
    }

    /**
     * 新增族谱信息
     * 
     * @param familyTree 族谱信息
     * @return 结果
     */
    @Override
    public int insertFamilyTree(FamilyTree familyTree)
    {
        return familyTreeMapper.insertFamilyTree(familyTree);
    }

    /**
     * 修改族谱信息
     * 
     * @param familyTree 族谱信息
     * @return 结果
     */
    @Override
    public int updateFamilyTree(FamilyTree familyTree)
    {
        return familyTreeMapper.updateFamilyTree(familyTree);
    }

    /**
     * 删除族谱信息
     * 
     * @param id 族谱信息主键
     * @return 结果
     */
    @Override
    public int deleteFamilyTreeById(Long id)
    {
        return familyTreeMapper.deleteFamilyTreeById(id);
    }

    /**
     * 批量删除族谱信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteFamilyTreeByIds(Long[] ids)
    {
        return familyTreeMapper.deleteFamilyTreeByIds(ids);
    }

    /**
     * 查询族谱成员
     * 
     * @param treeId 族谱ID
     * @return 成员列表
     */
    @Override
    public List<FamilyTreePerson> selectTreeMembers(Long treeId)
    {
        List<FamilyTreePerson> members = familyTreeMapper.selectTreeMembers(treeId);
        for (FamilyTreePerson member : members) {
            member.setPerson(familyPersonService.selectFamilyPersonById(member.getPersonId()));
        }
        return members;
    }

    /**
     * 查询人员所属族谱
     * 
     * @param personId 人员ID
     * @return 族谱列表
     */
    @Override
    public List<FamilyTreePerson> selectPersonTrees(Long personId)
    {
        List<FamilyTreePerson> trees = familyTreeMapper.selectPersonTrees(personId);
        for (FamilyTreePerson tree : trees) {
            tree.setTree(familyTreeMapper.selectFamilyTreeById(tree.getTreeId()));
        }
        return trees;
    }

    /**
     * 新增族谱-人员关联
     * 
     * @param treePerson 族谱-人员关联
     * @return 结果
     */
    @Override
    public int insertFamilyTreePerson(FamilyTreePerson treePerson)
    {
        return familyTreeMapper.insertFamilyTreePerson(treePerson);
    }

    /**
     * 修改族谱-人员关联
     * 
     * @param treePerson 族谱-人员关联
     * @return 结果
     */
    @Override
    public int updateFamilyTreePerson(FamilyTreePerson treePerson)
    {
        return familyTreeMapper.updateFamilyTreePerson(treePerson);
    }

    /**
     * 删除族谱-人员关联
     * 
     * @param id 关联ID
     * @return 结果
     */
    @Override
    public int deleteFamilyTreePersonById(Long id)
    {
        return familyTreeMapper.deleteFamilyTreePersonById(id);
    }

    /**
     * 批量删除族谱-人员关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteFamilyTreePersonByIds(Long[] ids)
    {
        return familyTreeMapper.deleteFamilyTreePersonByIds(ids);
    }

    /**
     * 按辈分查询族谱成员
     * 
     * @param treeId 族谱ID
     * @return 按辈分分组的成员列表
     */
    @Override
    public List<FamilyTreePerson> selectTreeMembersByGeneration(Long treeId)
    {
        List<FamilyTreePerson> members = familyTreeMapper.selectTreeMembersByGeneration(treeId);
        for (FamilyTreePerson member : members) {
            member.setPerson(familyPersonService.selectFamilyPersonById(member.getPersonId()));
        }
        return members;
    }

    /**
     * 获取族谱完整信息（包含成员）
     * 
     * @param id 族谱ID
     * @return 族谱完整信息
     */
    @Override
    public FamilyTree getFamilyTreeWithMembers(Long id)
    {
        FamilyTree tree = familyTreeMapper.selectFamilyTreeById(id);
        if (tree != null) {
            tree.setMembers(selectTreeMembers(id));
        }
        return tree;
    }

    /**
     * 获取族谱可视化数据
     * 
     * @param treeId 族谱ID
     * @return 可视化数据
     */
    @Override
    public Object getTreeVisualizationData(Long treeId)
    {
        List<FamilyTreePerson> members = selectTreeMembersByGeneration(treeId);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> links = new ArrayList<>();

        // 构建节点
        for (FamilyTreePerson member : members) {
            Map<String, Object> node = new HashMap<>();
            node.put("id", member.getPersonId());
            node.put("name", member.getPerson().getName());
            node.put("gender", member.getPerson().getGender());
            node.put("generation", member.getGeneration());
            node.put("description", member.getPerson().getDescription());
            nodes.add(node);

            // 构建关系
            if (member.getPerson().getFatherId() != null) {
                Map<String, Object> link = new HashMap<>();
                link.put("source", member.getPerson().getFatherId());
                link.put("target", member.getPersonId());
                link.put("relationship", "father");
                links.add(link);
            }
            if (member.getPerson().getMotherId() != null) {
                Map<String, Object> link = new HashMap<>();
                link.put("source", member.getPerson().getMotherId());
                link.put("target", member.getPersonId());
                link.put("relationship", "mother");
                links.add(link);
            }
            if (member.getPerson().getSpouseId() != null) {
                Map<String, Object> link = new HashMap<>();
                link.put("source", member.getPersonId());
                link.put("target", member.getPerson().getSpouseId());
                link.put("relationship", "spouse");
                links.add(link);
            }
        }

        result.put("nodes", nodes);
        result.put("links", links);
        return result;
    }
}
