package com.stock.eason.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.stock.eason.bean.Company;
import com.stock.eason.bean.IPOsPlanned;
import com.stock.eason.bean.StockExchange;

public interface AdminService {
    String queryContents();

	ArrayList<Company> getMatchingCompanies(HashMap<String, Object> param);

	Company getCompanyDetails(Integer id);

	IPOsPlanned getCompanyIPODetails(String companyName);

	String addCompany(Company zgGoods);

	String addIPOS(IPOsPlanned ipo);

	String addStockExchange(StockExchange se);

	ArrayList<IPOsPlanned> getCompanyIPOs();

	ArrayList<StockExchange> getStockExchange();
}
