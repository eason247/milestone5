package com.stock.eason.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.stock.eason.bean.StockPricedetailsExcel;
import com.stock.eason.util.DBUtil;

public class ExcelServiceImpl implements ExcelService{

	@Override
	public void saveDetail(StockPricedetailsExcel detail) {
		DBUtil.saveOrUpdate(detail);
	}

	@Override
	public ArrayList<StockPricedetailsExcel> queryData(HashMap<String, Object> param) {
		Iterator<Entry<String, Object>> entries = param.entrySet().iterator();
		String sql = "";
		while (entries.hasNext()) {
		  Entry<String, Object> entry = entries.next();
		  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		  sql = "from StockPricedetailsExcel where " + entry.getKey() + "=" + entry.getValue();
		}
		return DBUtil.selectByParam(sql);
		
	}
	
	
	
	

}
