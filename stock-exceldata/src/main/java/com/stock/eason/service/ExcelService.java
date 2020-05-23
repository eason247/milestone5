package com.stock.eason.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.stock.eason.bean.StockPricedetailsExcel;

public interface ExcelService {

	void saveDetail(StockPricedetailsExcel detail);

	ArrayList<StockPricedetailsExcel> queryData(HashMap<String, Object> param);

}
