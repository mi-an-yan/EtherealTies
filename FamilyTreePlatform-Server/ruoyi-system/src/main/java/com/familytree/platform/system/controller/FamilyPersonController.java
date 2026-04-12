package com.familytree.platform.system.controller;

import com.familytree.platform.common.annotation.Log;
import com.familytree.platform.common.core.controller.BaseController;
import com.familytree.platform.common.core.domain.AjaxResult;
import com.familytree.platform.common.core.page.TableDataInfo;
import com.familytree.platform.common.enums.BusinessType;
import com.familytree.platform.system.domain.FamilyPerson;
import com.familytree.platform.system.service.IFamilyPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 人员管理Controller
 */
@RestController
@RequestMapping("/family/person")
public class FamilyPersonController extends BaseController {

    @Autowired
    private IFamilyPersonService familyPersonService;

    /**
     * 查询人员列表
     */
    @PreAuthorize("@ss.hasPermi('family:person:list')")
    @GetMapping("/list")
    public TableDataInfo list(FamilyPerson familyPerson) {
        startPage();
        List<FamilyPerson> list = familyPersonService.selectFamilyPersonList(familyPerson);
        return getDataTable(list);
    }

    /**
     * 获取人员详细信息
     */
    @PreAuthorize("@ss.hasPermi('family:person:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(familyPersonService.selectFamilyPersonById(id));
    }

    /**
     * 新增人员
     */
    @PreAuthorize("@ss.hasPermi('family:person:add')")
    @Log(title = "人员管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FamilyPerson familyPerson) {
        return toAjax(familyPersonService.insertFamilyPerson(familyPerson));
    }

    /**
     * 修改人员
     */
    @PreAuthorize("@ss.hasPermi('family:person:edit')")
    @Log(title = "人员管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FamilyPerson familyPerson) {
        return toAjax(familyPersonService.updateFamilyPerson(familyPerson));
    }

    /**
     * 删除人员
     */
    @PreAuthorize("@ss.hasPermi('family:person:remove')")
    @Log(title = "人员管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(familyPersonService.deleteFamilyPersonByIds(ids));
    }

    /**
     * 导出人员列表
     */
    @PreAuthorize("@ss.hasPermi('family:person:export')")
    @Log(title = "人员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FamilyPerson familyPerson) {
        List<FamilyPerson> list = familyPersonService.selectFamilyPersonList(familyPerson);
        exportExcel(response, list, "人员数据");
    }
}