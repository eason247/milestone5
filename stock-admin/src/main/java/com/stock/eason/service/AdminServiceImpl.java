package com.stock.eason.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stock.eason.bean.Company;
import com.stock.eason.bean.IPOsPlanned;
import com.stock.eason.bean.StockExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

	@Override
	public ArrayList<Company> getMatchingCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company getCompanyDetails(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPOsPlanned getCompanyIPODetails(String companyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCompany(Company zgGoods) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addIPOS(IPOsPlanned ipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addStockExchange(StockExchange se) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IPOsPlanned> getCompanyIPOs(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<StockExchange> getStockExchange(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
