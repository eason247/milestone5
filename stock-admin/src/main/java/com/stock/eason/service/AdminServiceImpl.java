package com.stock.eason.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stock.eason.bean.Company;
import com.stock.eason.bean.IPOsPlanned;
import com.stock.eason.bean.StockExchange;
import com.stock.eason.util.DBUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class AdminServiceImpl implements AdminService {


    public static String SERVIER_NAME = "micro-order";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String queryContents() {
        //get  这个会选择一个服务列表调用
        String results = restTemplate.getForObject("http://"
                + SERVIER_NAME + "/queryUser", String.class);

        return results;
    }

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Company> getMatchingCompanies(HashMap<String, Object> param) {
		Iterator<Entry<String, Object>> entries = param.entrySet().iterator();
		String sql = "";
		ArrayList<String> arrayList = new ArrayList<String>();
		while (entries.hasNext()) {
		  Entry<String, Object> entry = entries.next();
		  sql = "from Company where " + entry.getKey() + "= ?";
		  arrayList.add(entry.getValue().toString());
		}
		return DBUtil.selectByParam(sql,arrayList,Company.class);
	}

	@Override
	public Company getCompanyDetails(Integer id) {
		Company company = (Company) DBUtil.selectById(id, Company.class);
		return company;
	}

	@Override
	public IPOsPlanned getCompanyIPODetails(String companyName) {
		
		return null;
	}

	@Override
	public String addCompany(Company company) {
		DBUtil.saveOrUpdate(company);
		return null;
	}

	@Override
	public String addIPOS(IPOsPlanned ipo) {
		DBUtil.saveOrUpdate(ipo);
		return null;
	}

	@Override
	public String addStockExchange(StockExchange se) {
		DBUtil.saveOrUpdate(se);
		return null;
	}

	@Override
	public ArrayList<IPOsPlanned> getCompanyIPOs() {
		ArrayList<String> arrayList = new ArrayList<String>();
		return DBUtil.selectByParam("from IPOs",arrayList,IPOsPlanned.class);
	}

	@Override
	public ArrayList<StockExchange> getStockExchange() {
		ArrayList<String> arrayList = new ArrayList<String>();
		return DBUtil.selectByParam("from StockExchange",arrayList,IPOsPlanned.class);
	}

}
