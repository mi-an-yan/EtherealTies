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
import com.familytree.platform.system.domain.FamilyHousehold;
import com.familytree.platform.system.domain.FamilyPersonHousehold;
import com.familytree.platform.system.service.IFamilyHouseholdService;

/**
 * 家庭信息Controller
 * 
 * @author familytree
 * @date 2026-04-12
 */
@Controller
@RequestMapping("/system/household")
public class FamilyHouseholdController extends BaseController
{
    @Autowired
    private IFamilyHouseholdService familyHouseholdService;

    /**
     * 查询家庭信息列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(FamilyHousehold familyHousehold)
    {
        startPage();
        List<FamilyHousehold> list = familyHouseholdService.selectFamilyHouseholdList(familyHousehold);
        return getDataTable(list);
    }

    /**
     * 导出家庭信息列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FamilyHousehold familyHousehold)
    {
        List<FamilyHousehold> list = familyHouseholdService.selectFamilyHouseholdList(familyHousehold);
        ExcelUtil<FamilyHousehold> util = new ExcelUtil<FamilyHousehold>(FamilyHousehold.class);
        return util.exportExcel(list, "家庭信息");
    }

    /**
     * 获取家庭信息详细信息
     */
    @GetMapping(value = { "/", "/{id}" })
    @ResponseBody
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id)
    {
        if (id != null)
        {
            return success(familyHouseholdService.getFamilyHouseholdWithMembers(id));
        }
        return success(new FamilyHousehold());
    }

    /**
     * 新增家庭信息
     */
    @PostMapping
    @ResponseBody
    public AjaxResult add(@RequestBody FamilyHousehold familyHousehold)
    {
        return toAjax(familyHouseholdService.insertFamilyHousehold(familyHousehold));
    }

    /**
     * 修改家庭信息
     */
    @PutMapping
    @ResponseBody
    public AjaxResult edit(@RequestBody FamilyHousehold familyHousehold)
    {
        return toAjax(familyHouseholdService.updateFamilyHousehold(familyHousehold));
    }

    /**
     * 删除家庭信息
     */
    @DeleteMapping("/{ids}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(familyHouseholdService.deleteFamilyHouseholdByIds(ids));
    }

    /**
     * 查询家庭成员
     */
    @GetMapping("/members/{householdId}")
    @ResponseBody
    public AjaxResult getMembers(@PathVariable Long householdId)
    {
        List<FamilyPersonHousehold> members = familyHouseholdService.selectHouseholdMembers(householdId);
        return success(members);
    }

    /**
     * 查询人员所属家庭
     */
    @GetMapping("/person/{personId}")
    @ResponseBody
    public AjaxResult getPersonHouseholds(@PathVariable Long personId)
    {
        List<FamilyPersonHousehold> households = familyHouseholdService.selectPersonHouseholds(personId);
        return success(households);
    }

    /**
     * 新增人员-家庭关联
     */
    @PostMapping("/member")
    @ResponseBody
    public AjaxResult addMember(@RequestBody FamilyPersonHousehold personHousehold)
    {
        return toAjax(familyHouseholdService.insertFamilyPersonHousehold(personHousehold));
    }

    /**
     * 修改人员-家庭关联
     */
    @PutMapping("/member")
    @ResponseBody
    public AjaxResult editMember(@RequestBody FamilyPersonHousehold personHousehold)
    {
        return toAjax(familyHouseholdService.updateFamilyPersonHousehold(personHousehold));
    }

    /**
     * 删除人员-家庭关联
     */
    @DeleteMapping("/member/{ids}")
    @ResponseBody
    public AjaxResult removeMember(@PathVariable Long[] ids)
    {
        return toAjax(familyHouseholdService.deleteFamilyPersonHouseholdByIds(ids));
    }
}
