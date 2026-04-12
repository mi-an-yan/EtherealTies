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
import com.familytree.platform.system.domain.FamilyPerson;
import com.familytree.platform.system.service.IFamilyPersonService;

/**
 * 人员信息Controller
 * 
 * @author familytree
 * @date 2026-04-12
 */
@Controller
@RequestMapping("/system/person")
public class FamilyPersonController extends BaseController
{
    @Autowired
    private IFamilyPersonService familyPersonService;

    /**
     * 查询人员信息列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(FamilyPerson familyPerson)
    {
        startPage();
        List<FamilyPerson> list = familyPersonService.selectFamilyPersonList(familyPerson);
        return getDataTable(list);
    }

    /**
     * 导出人员信息列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FamilyPerson familyPerson)
    {
        List<FamilyPerson> list = familyPersonService.selectFamilyPersonList(familyPerson);
        ExcelUtil<FamilyPerson> util = new ExcelUtil<FamilyPerson>(FamilyPerson.class);
        return util.exportExcel(list, "人员信息");
    }

    /**
     * 获取人员信息详细信息
     */
    @GetMapping(value = { "/", "/{id}" })
    @ResponseBody
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id)
    {
        if (id != null)
        {
            return success(familyPersonService.getFamilyPersonWithRelations(id));
        }
        return success(new FamilyPerson());
    }

    /**
     * 新增人员信息
     */
    @PostMapping
    @ResponseBody
    public AjaxResult add(@RequestBody FamilyPerson familyPerson)
    {
        return toAjax(familyPersonService.insertFamilyPerson(familyPerson));
    }

    /**
     * 修改人员信息
     */
    @PutMapping
    @ResponseBody
    public AjaxResult edit(@RequestBody FamilyPerson familyPerson)
    {
        return toAjax(familyPersonService.updateFamilyPerson(familyPerson));
    }

    /**
     * 删除人员信息
     */
    @DeleteMapping("/{ids}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(familyPersonService.deleteFamilyPersonByIds(ids));
    }

    /**
     * 查询子女列表
     */
    @GetMapping("/children/{parentId}")
    @ResponseBody
    public AjaxResult getChildren(@PathVariable Long parentId)
    {
        List<FamilyPerson> children = familyPersonService.selectChildrenByFatherId(parentId);
        children.addAll(familyPersonService.selectChildrenByMotherId(parentId));
        return success(children);
    }

    /**
     * 搜索人员
     */
    @GetMapping("/search")
    @ResponseBody
    public AjaxResult search(String keyword)
    {
        List<FamilyPerson> list = familyPersonService.searchFamilyPersons(keyword);
        return success(list);
    }
}
