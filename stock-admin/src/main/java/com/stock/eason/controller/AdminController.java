package com.stock.eason.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.eason.bean.Company;
import com.stock.eason.bean.IPOsPlanned;
import com.stock.eason.bean.StockExchange;
import com.stock.eason.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/getMatchingCompanies ")
    public ArrayList<Company> getMatchingCompanies(HttpServletRequest request) {
        return adminService.getMatchingCompanies();
    }
    @RequestMapping("/getCompanyDetails  ")
    public Company getCompanyDetails (Integer id) {
    	return adminService.getCompanyDetails(id);
    }
    @RequestMapping("/getCompanyIPODetails  ")
    public IPOsPlanned getCompanyIPODetails (String companyName) {
    	return adminService.getCompanyIPODetails(companyName);
    }
    @RequestMapping("/getCompanyIPOs  ")
    public ArrayList<IPOsPlanned> getCompanyIPOs (Integer id) {
    	return adminService.getCompanyIPOs(id);
    }

	@RequestMapping(value = "/addCompany",method = RequestMethod.POST)
	public String addCompany(@RequestBody Company company) {
		return adminService.addCompany(company);
	}
	
	@RequestMapping(value = "/addIPOS",method = RequestMethod.POST)
	public String addIPOS(@RequestBody IPOsPlanned ipo) {
		return adminService.addIPOS(ipo);
	}
    
	@RequestMapping(value = "/addStockExchange",method = RequestMethod.POST)
	public String addStockExchange(@RequestBody StockExchange se) {
		return adminService.addStockExchange(se);
	}
	
    @RequestMapping("/getStockExchange  ")
    public ArrayList<StockExchange> getStockExchange (Integer id) {
    	return adminService.getStockExchange(id);
    }
    public static boolean canVisitDb = true;
    /*
    * 这个接口只为了检测db连接是否ok
    * */
    @RequestMapping("/db/{can}")
    public void setDb(@PathVariable boolean can) {
        canVisitDb = can;
    }
}
