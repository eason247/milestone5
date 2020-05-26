package com.stock.eason.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stock.eason.bean.Company;
import com.stock.eason.bean.StockPricedetailsExcel;
import com.stock.eason.service.ExcelService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {
	
    @Autowired
    private ExcelService excelService;

	@PostMapping("/import")
	public void  importExcel(@RequestParam("test_e") MultipartFile file)throws Exception{
		Workbook hssfWorkbook  = null;

		StockPricedetailsExcel detail = null;
		ArrayList<StockPricedetailsExcel> list = new ArrayList<StockPricedetailsExcel>();
    	// 循环工作表Sheet
    	for (int numSheet = 0; numSheet <hssfWorkbook.getNumberOfSheets(); numSheet++) {
    		//HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet); 
    		Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
    		if (hssfSheet == null) {
    			continue;
    		}
    		// 循环行Row
    		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
    			//HSSFRow hssfRow = hssfSheet.getRow(rowNum);
    			Row hssfRow = hssfSheet.getRow(rowNum);
    			if (hssfRow != null) {
//    				student = new User();
    				//HSSFCell name = hssfRow.getCell(0);
    				//HSSFCell pwd = hssfRow.getCell(1);
    				Cell companyCode = hssfRow.getCell(0);
    				Cell currentPrice = hssfRow.getCell(1);
    				Cell dateoftheStockPrice = hssfRow.getCell(2);
    				Cell stockPriceatthisSpecifictime = hssfRow.getCell(3);
//    				//这里是自己的逻辑
//    				student.setUserName(name.toString());
//    				student.setPassword(pwd.toString());
//    				list.add(student);
    				detail.setCompanyCode(companyCode.toString());
    				detail.setCurrentPrice(currentPrice.toString());
    				detail.setDateoftheStockPrice(dateoftheStockPrice.toString());
    				detail.setStockPriceatthisSpecifictime(stockPriceatthisSpecifictime.toString());
    				excelService.saveDetail(detail);
    			}
    		}
    	}

	}
   
    
    
    @RequestMapping("/queryData")
    public ArrayList<StockPricedetailsExcel> queryData(@RequestBody HashMap<String, Object> param) {
        return excelService.queryData(param);
    }
    
    @RequestMapping("/queryData")
    public ArrayList<StockPricedetailsExcel> generateReportDataByCompany(String company) {
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("companyName", company);
        return excelService.queryData(param);
    }
    
    @RequestMapping("/queryData")
    public ArrayList<StockPricedetailsExcel> generateReportDataByDate(String dateoftheStockPrice) {
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("dateoftheStockPrice", dateoftheStockPrice);
        return excelService.queryData(param);
    }
}
