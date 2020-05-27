package com.stock.eason.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.netflix.client.http.HttpRequest;
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
	@PostMapping("/exportToExcel")
	public void  exportToExcel( HttpServletRequest  request ,  HttpServletResponse response,HttpSession session,@RequestBody HashMap<String, Object> param)throws Exception{
        String randomNumber = request.getParameter("randomNumber");// session名称
        try {
            session = request.getSession();
            session.setAttribute(randomNumber, new Double(1));
            // 导出的EXCEL文件名
            String exportFileName = "addressBook.xlsx";
            response.reset();
            response.setContentType("octets/stream");
            // response.setHeader("Content-Disposition","attachment;filename="+exportFileName);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(exportFileName.getBytes("UTF-8"), "iso8859-1") + "\"");

            // 导出的EXCEL列属性
            ArrayList<StockPricedetailsExcel> columnListName = excelService.queryData(param);
            Bean2ExcelConversionUtils.beans2excelFile07(columnListName, data, response.getOutputStream());
            session.setAttribute(randomNumber, new Double(100));

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute(randomNumber, new Double(100));
        } catch (Throwable e) {
            e.printStackTrace();
            session.setAttribute(randomNumber, new Double(100));
        }
		
	}
	
	
   
    
    
    @RequestMapping("/queryData")
    public ArrayList<StockPricedetailsExcel> queryData(@RequestBody HashMap<String, Object> param) {
        return excelService.queryData(param);
    }
    
    @RequestMapping("/generateReportDataByCompany")
    public ArrayList<StockPricedetailsExcel> generateReportDataByCompany(String company) {
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("companyName", company);
        return excelService.queryData(param);
    }
    
    @RequestMapping("/generateReportDataByDate")
    public ArrayList<StockPricedetailsExcel> generateReportDataByDate(String dateoftheStockPrice) {
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("dateoftheStockPrice", dateoftheStockPrice);
        return excelService.queryData(param);
    }
}
