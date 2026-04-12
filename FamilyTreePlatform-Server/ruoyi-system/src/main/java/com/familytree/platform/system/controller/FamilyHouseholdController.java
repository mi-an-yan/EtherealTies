package com.familytree.platform.system.controller;

import com.familytree.platform.common.annotation.Log;
import com.familytree.platform.common.core.controller.BaseController;
import com.familytree.platform.common.core.domain.AjaxResult;
import com.familytree.platform.common.core.page.TableDataInfo;
import com.familytree.platform.common.enums.BusinessType;
import com.familytree.platform.system.domain.FamilyHousehold;
import com.familytree.platform.system.service.IFamilyHouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 家庭管理Controller
 */
@RestController
@RequestMapping("/family/household")
public class FamilyHouseholdController extends BaseController {

    @Autowired
    private IFamilyHouseholdService familyHouseholdService;

    /**
     * 查询家庭列表
     */
    @PreAuthorize("@ss.hasPermi('family:household:list')")
    @GetMapping("/list")
    public TableDataInfo list(FamilyHousehold familyHousehold) {
        startPage();
        List<FamilyHousehold> list = familyHouseholdService.selectFamilyHouseholdList(familyHousehold);
        return getDataTable(list);
    }

    /**
     * 获取家庭详细信息
     */
    @PreAuthorize("@ss.hasPermi('family:household:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(familyHouseholdService.selectFamilyHouseholdById(id));
    }

    /**
     * 新增家庭
     */
    @PreAuthorize("@ss.hasPermi('family:household:add')")
    @Log(title = "家庭管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FamilyHousehold familyHousehold) {
        return toAjax(familyHouseholdService.insertFamilyHousehold(familyHousehold));
    }

    /**
     * 修改家庭
     */
    @PreAuthorize("@ss.hasPermi('family:household:edit')")
    @Log(title = "家庭管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FamilyHousehold familyHousehold) {
        return toAjax(familyHouseholdService.updateFamilyHousehold(familyHousehold));
    }

    /**
     * 删除家庭
     */
    @PreAuthorize("@ss.hasPermi('family:household:remove')")
    @Log(title = "家庭管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(familyHouseholdService.deleteFamilyHouseholdByIds(ids));
    }

    /**
     * 导出家庭列表
     */
    @PreAuthorize("@ss.hasPermi('family:household:export')")
    @Log(title = "家庭管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FamilyHousehold familyHousehold) {
        List<FamilyHousehold> list = familyHouseholdService.selectFamilyHouseholdList(familyHousehold);
        exportExcel(response, list, "家庭数据");
    }
}