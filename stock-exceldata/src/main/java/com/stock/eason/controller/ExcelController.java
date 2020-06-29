package com.stock.eason.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.stock.eason.bean.StockPricedetailsExcel;
import com.stock.eason.service.ExcelService;
import com.stock.eason.util.ExcelUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {
	
    @Autowired
    private ExcelService excelService;

	@PostMapping("/import")
	public String  importExcel(HttpServletRequest request)throws Exception{
	    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartHttpServletRequest.getFile("excelFile");
        if(file.isEmpty()) {
            return "redirect:/admin/course/list";
        }

        try {
            InputStream inputStream = file.getInputStream();
            List<List<Object>> list = ExcelUtils.getCourseListByExcel(inputStream, file.getOriginalFilename());
            inputStream.close();

            for (int i = 0; i < list.size(); i++) {
                List<Object> excelList = list.get(i);

                StockPricedetailsExcel course = new StockPricedetailsExcel();

                course.setCompanyCode(excelList.get(0).toString());
                course.setCurrentPrice(excelList.get(1).toString());
                Date date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(excelList.get(2).toString());
                course.setStockPriceatthisSpecifictime(new SimpleDateFormat("yyyy-MM-dd").format(date1));
                // 通过教师姓名查教师id

                // 格式化时间
                Date date2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(excelList.get(3).toString());
                course.setDateoftheStockPrice(new SimpleDateFormat("yyyy-MM-dd").format(date2));


                // 执行插入操作
                excelService.saveDetail(course);
            }
        } catch (Exception e) {
            return "redirect:/admin/course/list";
        }

        return "redirect:/admin/course/list";

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
//            Bean2ExcelConversionUtils.beans2excelFile07(columnListName, data, response.getOutputStream());
            session.setAttribute(randomNumber, new Double(100));

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute(randomNumber, new Double(100));
        } catch (Throwable e) {
            e.printStackTrace();
            session.setAttribute(randomNumber, new Double(100));
        }
//		
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
