package com.stock.eason.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ExcelController {

	@PostMapping("/import")
	public void  importExcel(@RequestParam("test_e") MultipartFile file)throws Exception{
		Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheet(0);
        for (int j = 1; j < sheet.getRows(); j++) {//j=1,跳过第一行，标题
            String cellinfo = sheet.getCell(0, j).getContents();//读取的是第二行(j)，第一列(0)数据
            cellinfo = sheet.getCell(1, j).getContents();//读取的是第二行(j)，第二列(1)数据
            cellinfo = sheet.getCell(2, j).getContents();//读取的是第二行(j)，第三列(2)数据
        }
	}
   
    
    
    @RequestMapping("/queryData")
    public String queryData() {
//        log.info("========micro-order===queryUser");
        return "========micro-order===queryUser";
    }
}
