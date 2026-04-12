package com.familytree.platform.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.familytree.platform.common.core.controller.BaseController;
import com.familytree.platform.common.core.domain.AjaxResult;
import com.familytree.platform.common.utils.poi.ExcelUtil;
import com.familytree.platform.common.core.page.TableDataInfo;
import com.familytree.platform.system.domain.FamilyTree;
import com.familytree.platform.system.domain.FamilyTreePerson;
import com.familytree.platform.system.service.IFamilyTreeService;

/**
 * 族谱信息Controller
 * 
 * @author familytree
 * @date 2026-04-12
 */
@Controller
@RequestMapping("/system/tree")
public class FamilyTreeController extends BaseController
{
    @Autowired
    private IFamilyTreeService familyTreeService;

    /**
     * 查询族谱信息列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(FamilyTree familyTree)
    {
        startPage();
        List<FamilyTree> list = familyTreeService.selectFamilyTreeList(familyTree);
        return getDataTable(list);
    }

    /**
     * 导出族谱信息列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FamilyTree familyTree)
    {
        List<FamilyTree> list = familyTreeService.selectFamilyTreeList(familyTree);
        ExcelUtil<FamilyTree> util = new ExcelUtil<FamilyTree>(FamilyTree.class);
        return util.exportExcel(list, "族谱信息");
    }

    /**
     * 获取族谱信息详细信息
     */
    @GetMapping(value = { "/", "/{id}" })
    @ResponseBody
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id)
    {
        if (id != null)
        {
            return success(familyTreeService.getFamilyTreeWithMembers(id));
        }
        return success(new FamilyTree());
    }

    /**
     * 新增族谱信息
     */
    @PostMapping
    @ResponseBody
    public AjaxResult add(@RequestBody FamilyTree familyTree)
    {
        return toAjax(familyTreeService.insertFamilyTree(familyTree));
    }

    /**
     * 修改族谱信息
     */
    @PutMapping
    @ResponseBody
    public AjaxResult edit(@RequestBody FamilyTree familyTree)
    {
        return toAjax(familyTreeService.updateFamilyTree(familyTree));
    }

    /**
     * 删除族谱信息
     */
    @DeleteMapping("/{ids}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(familyTreeService.deleteFamilyTreeByIds(ids));
    }

    /**
     * 查询族谱成员
     */
    @GetMapping("/members/{treeId}")
    @ResponseBody
    public AjaxResult getMembers(@PathVariable Long treeId)
    {
        List<FamilyTreePerson> members = familyTreeService.selectTreeMembers(treeId);
        return success(members);
    }

    /**
     * 查询人员所属族谱
     */
    @GetMapping("/person/{personId}")
    @ResponseBody
    public AjaxResult getPersonTrees(@PathVariable Long personId)
    {
        List<FamilyTreePerson> trees = familyTreeService.selectPersonTrees(personId);
        return success(trees);
    }

    /**
     * 新增族谱-人员关联
     */
    @PostMapping("/member")
    @ResponseBody
    public AjaxResult addMember(@RequestBody FamilyTreePerson treePerson)
    {
        return toAjax(familyTreeService.insertFamilyTreePerson(treePerson));
    }

    /**
     * 修改族谱-人员关联
     */
    @PutMapping("/member")
    @ResponseBody
    public AjaxResult editMember(@RequestBody FamilyTreePerson treePerson)
    {
        return toAjax(familyTreeService.updateFamilyTreePerson(treePerson));
    }

    /**
     * 删除族谱-人员关联
     */
    @DeleteMapping("/member/{ids}")
    @ResponseBody
    public AjaxResult removeMember(@PathVariable Long[] ids)
    {
        return toAjax(familyTreeService.deleteFamilyTreePersonByIds(ids));
    }

    /**
     * 按辈分查询族谱成员
     */
    @GetMapping("/members/generation/{treeId}")
    @ResponseBody
    public AjaxResult getMembersByGeneration(@PathVariable Long treeId)
    {
        List<FamilyTreePerson> members = familyTreeService.selectTreeMembersByGeneration(treeId);
        return success(members);
    }

    /**
     * 获取族谱可视化数据
     */
    @GetMapping("/visualization/{treeId}")
    @ResponseBody
    public AjaxResult getVisualizationData(@PathVariable Long treeId)
    {
        Object data = familyTreeService.getTreeVisualizationData(treeId);
        return success(data);
    }
}
