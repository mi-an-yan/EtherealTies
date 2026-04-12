package com.familytree.platform.system.controller;

import com.familytree.platform.common.annotation.Log;
import com.familytree.platform.common.core.controller.BaseController;
import com.familytree.platform.common.core.domain.AjaxResult;
import com.familytree.platform.common.core.page.TableDataInfo;
import com.familytree.platform.common.enums.BusinessType;
import com.familytree.platform.system.domain.FamilyTree;
import com.familytree.platform.system.service.IFamilyTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 族谱管理Controller
 */
@RestController
@RequestMapping("/family/tree")
public class FamilyTreeController extends BaseController {

    @Autowired
    private IFamilyTreeService familyTreeService;

    /**
     * 查询族谱列表
     */
    @PreAuthorize("@ss.hasPermi('family:tree:list')")
    @GetMapping("/list")
    public TableDataInfo list(FamilyTree familyTree) {
        startPage();
        List<FamilyTree> list = familyTreeService.selectFamilyTreeList(familyTree);
        return getDataTable(list);
    }

    /**
     * 获取族谱详细信息
     */
    @PreAuthorize("@ss.hasPermi('family:tree:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(familyTreeService.selectFamilyTreeById(id));
    }

    /**
     * 新增族谱
     */
    @PreAuthorize("@ss.hasPermi('family:tree:add')")
    @Log(title = "族谱管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FamilyTree familyTree) {
        return toAjax(familyTreeService.insertFamilyTree(familyTree));
    }

    /**
     * 修改族谱
     */
    @PreAuthorize("@ss.hasPermi('family:tree:edit')")
    @Log(title = "族谱管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FamilyTree familyTree) {
        return toAjax(familyTreeService.updateFamilyTree(familyTree));
    }

    /**
     * 删除族谱
     */
    @PreAuthorize("@ss.hasPermi('family:tree:remove')")
    @Log(title = "族谱管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(familyTreeService.deleteFamilyTreeByIds(ids));
    }

    /**
     * 导出族谱列表
     */
    @PreAuthorize("@ss.hasPermi('family:tree:export')")
    @Log(title = "族谱管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FamilyTree familyTree) {
        List<FamilyTree> list = familyTreeService.selectFamilyTreeList(familyTree);
        exportExcel(response, list, "族谱数据");
    }

    /**
     * 获取族谱可视化数据
     */
    @PreAuthorize("@ss.hasPermi('family:tree:view')")
    @GetMapping("/visualize/{id}")
    public AjaxResult visualize(@PathVariable("id") Long id) {
        return success(familyTreeService.getTreeVisualizationData(id));
    }
}