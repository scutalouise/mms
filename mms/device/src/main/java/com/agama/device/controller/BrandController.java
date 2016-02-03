package com.agama.device.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agama.common.dao.utils.Page;
import com.agama.common.dao.utils.PropertyFilter;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.IsInitialEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.common.web.BaseController;
import com.agama.device.domain.Brand;
import com.agama.device.domain.DeviceInventory;
import com.agama.device.service.IBrandService;
import com.agama.device.service.IDeviceInventoryService;

@Controller
@RequestMapping("device/brand")
public class BrandController extends BaseController {

	@Autowired
	private IBrandService brandService;
	@Autowired
	private IDeviceInventoryService deviceInventoryService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "brand/brandList";
	}

	/**
	 * 获取品牌json
	 * 
	 */
	@RequiresPermissions("device:brand:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<Brand> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = brandService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加品牌弹窗
	 */
	@RequiresPermissions("device:brand:add")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("brand", new Brand());
		// addAttribute("firstdeviceType", FirstDeviceType.values());
		model.addAttribute("action", "add");
		return "brand/brandForm";
	}

	/**
	 * 添加品牌
	 */
	@RequiresPermissions("device:brand:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Brand brand) {
		brand.setUpdateTime(new Date());
		brand.setStatus(StatusEnum.NORMAL);
		brandService.save(brand);
		return "success";
	}

	/**
	 * 修改品牌弹窗
	 */
	@RequiresPermissions("device:brand:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable Integer id, Model model) {
		model.addAttribute("brand", brandService.get(id));
		model.addAttribute("action", "update");
		return "brand/brandForm";
	}

	/**
	 * 修改品牌
	 */
	@RequiresPermissions("device:brand:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid Brand brand) {
		DeviceInventory deviceInventory = deviceInventoryService.getByBrandId(brand.getId());
		if (deviceInventory != null && brand.getEnable()==EnabledStateEnum.ENABLED
				 && brand.getFirstDeviceType()==deviceInventory.getFirstDeviceType()
				 && brand.getSecondDeviceType()==deviceInventory.getSecondDeviceType()) {
			deviceInventory.setBrandName(brand.getName());
			deviceInventoryService.update(deviceInventory);
		}else if(deviceInventory == null){
			
		}else{
			return "false"; 
		}
		brand.setUpdateTime(new Date());
		brand.setStatus(StatusEnum.NORMAL);
		brandService.update(brand);
		return "success";
	}

	/**
	 * 逻辑删除品牌
	 */
	@RequiresPermissions("device:brand:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id) {
		Brand brand = brandService.get(id);
		DeviceInventory deviceInventory = deviceInventoryService.getByBrandId(id);
		if (brand.getIsInitial() == IsInitialEnum.INIT) {
			return "false";
		} else if (deviceInventory != null) {
			return "false";
		}
		brandService.updateStatusById(id);
		return "success";
	}

	/**
	 * 获取一级设备类型集合
	 */
	@RequestMapping(value = "firstDeviceType", method = RequestMethod.GET)
	@ResponseBody
	public List<FirstDeviceType> getFirstDeviceType() {
		return FirstDeviceType.getFirstDeviceType();
	}

	/**
	 * 根据一级设备类型获取二级设备类型集合
	 */
	@RequestMapping(value = "secondDeviceType", method = RequestMethod.GET)
	@ResponseBody
	public List<SecondDeviceType> getSecondDeviceTypeByFirstDeviceType(@RequestParam FirstDeviceType firstDeviceType) {
		return SecondDeviceType.getSecondDeviceTypeByFirstDeviceType(firstDeviceType);
	}

	/**
	 * 根据二级设备类型获取品牌集合
	 */
	@RequestMapping(value = "brandCollection", method = RequestMethod.GET)
	@ResponseBody
	public List<Brand> getBrandBySecondDeviceType(@RequestParam SecondDeviceType secondDeviceType) {
		return brandService.getBrandBySecondDeviceType(secondDeviceType);
	}

	/**
	 * 根据二级设备类型检查品牌名唯一
	 */
	@RequestMapping(value = "checkBrandName")
	@ResponseBody
	public boolean checkBrandName(@RequestParam Integer id, @RequestParam String brandName,
			@RequestParam SecondDeviceType secondDeviceType) {
		boolean flag = true;
		if (secondDeviceType != null) {
			List<Brand> brands = brandService.validBrand(secondDeviceType, id);
			for (Brand brand : brands) {
				if (brand.getName().equals(brandName)) {
					flag = false;
				}
			}
		}
		return flag;
	}
}
