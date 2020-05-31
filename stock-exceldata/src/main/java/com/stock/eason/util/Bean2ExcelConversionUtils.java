package com.stock.eason.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * @author Tan Jiangyong
 * @date 2013-9-3 下午3:36:43
 * @version V1.0
 */
@SuppressWarnings("all")
public class Bean2ExcelConversionUtils {
    private static final String PATTERN="yyyy-MM-dd HH:mm:ss";    //excel日期格式，默认配置
    private static final String DATE_PATTERN="yyyy-MM-dd";        //excel日期格式
    private static final String DATE_HH_PATTERN="HH:mm:ss";        //excel时间格式
    private static final int TOTAL_SIZE=40000;                    //每个excel写入多少数据（默认配置）
    private static final int MAX_SHEET_SIZE=10000;                //每一个sheet的大小（默认配置）
    private static final int COLUMN_WIDTH_WORD=25;                //列宽，默认汉字个数为25个
    private static final int FLUSH_ROWS=100;                    //每生成excel行数，内存中缓存记录数清空（目的，避免零时文件过大）
    
    /**
     * 07、10办公版EXCEL导出（数据直接写到服务器的EXCEL里，以下载的形式，下载导出的数据）
     * @param listName        列表头名称
     * @param beans         实体集合
     * @param result        数字字典Map集
     * @param filePath        服务器存放文件路径
     * @param fileName         文件名称
     * @param totalSize     EXCEL条数量
     * @param maxSheetSize     sheet页条数量
     * @return              文件集合
     * @throws Exception    
     */
    public static <T> List<File> beans2excelFile07(List<String> listName,List<T> beans,HashMap<String,HashMap<String,String>> result,String filePath,String fileName,Integer totalSize,Integer maxSheetSize) throws Exception{
        if(totalSize==null || totalSize<=0)
            totalSize=TOTAL_SIZE;
        if(maxSheetSize==null || maxSheetSize<=0)
            maxSheetSize=MAX_SHEET_SIZE;
        if(fileName==null)
            fileName="";
        return beans2excelFile2007(listName, beans, result, filePath, fileName,totalSize,maxSheetSize);
    }
    /**
     * 07、10办公版EXCEL导出（数据直接写到服务器的EXCEL里，以下载的形式，下载导出的数据）
     * @param listName        列表头名称
     * @param beans         实体集合
     * @param result        数字字典Map集
     * @param filePath        服务器存放文件路径
     * @param fileName         文件名称
     * @param totalSize     EXCEL条数量
     * @param maxSheetSize     sheet页条数量
     * @param request        客户端请求对象
     * @param response        客户端响应对象
     * @throws Exception
     */
    public static <T> void beans2excelFile07(List<String> listName,List<T> beans,HashMap<String,HashMap<String,String>> result,String filePath,String fileName,Integer totalSize,Integer maxSheetSize,HttpServletRequest request,HttpServletResponse response) throws Exception{
        if(totalSize==null || totalSize<=0)
            totalSize=TOTAL_SIZE;
        if(maxSheetSize==null || maxSheetSize<=0)
            maxSheetSize=MAX_SHEET_SIZE;
        if(fileName==null)
            fileName="";
        List<File> files = beans2excelFile2007(listName, beans, result, filePath, fileName,totalSize,maxSheetSize);
        DownLoadUtils.downLoadFiles(files, filePath, request, response);
    }
    
    /**
     * 07、10办公版EXCEL导出，每个EXCEL组织数据
     * @param listName         列表头名称
     * @param beans             实体集合
     * @param result         数字字典Map集
     * @param filePath         服务器存放文件路径
     * @param fileName         文件名称
     * @param totalSize      EXCEL条数量
     * @param maxSheetSize  sheet页条数量
     * @return                文件集合
     * @throws Exception
     */
    private static <T> List<File> beans2excelFile2007(List<String> listName,List<T> beans,HashMap<String,HashMap<String,String>> result,String filePath,String fileName,Integer totalSize,Integer maxSheetSize) throws Exception{
        if ((listName == null) || (listName.size() == 0)) {
            throw new Exception("listName is null when create excel document");
        }
        List<File> listFile=new ArrayList<File>();//返回的文件集合
        
        int size=beans==null?0:beans.size();
        String fileSuffixName=".xlsx";//后缀
        String path="";//文件路径
        Integer startIdx=0;//数据读取的起始行
        Integer endIdx=0;//数据读取的结束行
        (new File(filePath)).mkdirs(); //没有该目录创建目录
        if(size==0){
            startIdx=0;
            endIdx=(totalSize)>size?size:(totalSize);
            String name=fileName+"_第0-0条数据";
            path=filePath+File.separatorChar+name+fileSuffixName;
            Workbook wb =new SXSSFWorkbook();
            buildExcelDocument2007(wb, listName, beans,result,startIdx,endIdx,maxSheetSize);
            //没有文件，创建文件
            File file = new File(path);
            if (!file.exists()){  
                file.createNewFile();  
            }
            FileOutputStream out=new FileOutputStream(file);
            wb.write(out);
            out.close();
            return listFile;
        }
        for (int i = 0; i < size;i++) {
            int remainder=i%totalSize;
            if(size==0 || remainder==0){
                startIdx=i;
                endIdx=(i+totalSize)>size?size:(i+totalSize);
                String name=fileName+"_第"+(startIdx+1)+"-"+(endIdx)+"条数据";
                path=filePath+"/"+name+fileSuffixName;
                Workbook wb =new SXSSFWorkbook();
                buildExcelDocument2007(wb, listName, beans,result,startIdx,endIdx,maxSheetSize);
                //没有文件，创建文件
                File file = new File(path);
                if (!file.exists()){  
                    file.createNewFile();  
                }
                FileOutputStream out=new FileOutputStream(file);
                wb.write(out);
                out.close();
                listFile.add(file);
            }else if((size-i)<totalSize && i>endIdx){//最后，不满一万条
                startIdx=i;
                endIdx=i+totalSize;
                String name=fileName+"_第"+(startIdx+1)+"-"+(endIdx)+"条数据";
                path=filePath+name+"."+fileSuffixName;//没有文件，创建文件
                Workbook wb =new SXSSFWorkbook();
                buildExcelDocument2007(wb, listName, beans, result,startIdx,endIdx,maxSheetSize);
                //没有文件，创建文件
                File file = new File(path);
                if (!file.exists()){  
                    file.createNewFile();  
                }
                FileOutputStream out=new FileOutputStream(file);
                wb.write(out);
                out.close();
                listFile.add(file);
            }
        }
        return listFile;
    }
    
    /**
     * 07、10办公版EXCEL导出，每个EXCEL写入数据
     * @param wb            EXCEL工作薄
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param result        数字字典Map集
     * @param startIdx        数据集合，开始行
     * @param endIdx        数据集合，结束始行
     * @param maxSheetSize    SHEET页条数
     * @throws Exception 
     */
    private static <T> void buildExcelDocument2007(Workbook wb, List<String> listName, List<T> beans,HashMap<String,HashMap<String,String>> result,Integer startIdx,Integer endIdx,Integer maxSheetSize) throws Exception
    {
        int totalSize=endIdx-startIdx;//总共条数
        try
        {
            CellStyle cellStyle=POIUtils.getCellStyleFont(wb,null);
            List titles = new ArrayList();
            List beanAttrNames = new ArrayList();
            boolean flagListExists=false;
            List flagList=new ArrayList();
            List widthList=new ArrayList();
            HashMap<String,String> dateMap=new HashMap<String, String>();
            String[] header = new String[listName.size()];
            int rows_max = 0;//标题占多少列
            for (int i=0;i<listName.size();i++)
            {
                String[] str=listName.get(i).split("&");
                String en_name=str[0];
                String zh_name=str[1];
                beanAttrNames.add(i,en_name);
                titles.add(i, zh_name);
                header[i]=zh_name;
                if (zh_name.split("_").length > rows_max) {
                    rows_max = zh_name.split("_").length;
                }
                if(str.length>2){
                    String flag=str[2];
                    flagList.add(i,flag);
                    if(!flagListExists)
                        flagListExists=true;
                }
                if(str.length>3){
                    widthList.add(str[3]);
                }
                if(str.length>4){
                    dateMap.put(en_name, str[4]);
                }
            }

            PropertyDescriptor[] props = null;

            int size=endIdx-startIdx;
            Sheet sheet=null;
            
            //如果没有数据，导出表头
            if(size==0){
                sheet=ExcelHeadUtils.getExcelHead2007(wb, header, "Sheet1");
                sheet.setDefaultRowHeight((short)350);//高度
                setColumnWidth2007(widthList, sheet,beanAttrNames.size());
                return ;
            }
            int u=1;//用来创建每个sheet的行
            int h=0;//用来标注每个sheet也得名字：多少行-多少行
            for (int i = startIdx; i < endIdx ; i++) {
                int remainder=h%maxSheetSize;
                if(size==0 || i==startIdx || remainder==0){
                    u=1;
                    int section=(h+maxSheetSize)>totalSize?totalSize:(h+maxSheetSize);
                    sheet=ExcelHeadUtils.getExcelHead2007(wb, header,"第"+(h+1)+"-"+section+"条");
                    sheet.createFreezePane( 1, rows_max, 1, rows_max);
                    sheet.setDefaultRowHeight((short)350);//高度
                    setColumnWidth2007(widthList, sheet,beanAttrNames.size());
                }
                if(props==null)
                    props=Introspector.getBeanInfo(beans.get(0).getClass()).getPropertyDescriptors();
                Object bean = beans.get(i);
                Row row = sheet.createRow(u+rows_max-1);
                u++;
                h++;
                for (int j = 0; j < beanAttrNames.size(); j++) {
                    String beanAttrName = (String)beanAttrNames.get(j);
                    String flag="";
                    if(flagListExists)
                        flag=(String)flagList.get(j);
                    for (int k = 0; k < props.length; k++) {
                        String propName = props[k].getName();
                        if (propName.equals(beanAttrName))
                        {
                            String pattern=dateMap.get(beanAttrName);
                            Cell cell = row.createCell((short)j);

                            Object cellValue = callGetter(bean, props[k],pattern);
                            if("true".equalsIgnoreCase(flag)){
                                if(result!=null){
                                    HashMap<String,String> hash=result.get(beanAttrName);
                                    if(hash!=null)
                                        cellValue=hash.get(cellValue);
                                }
                            }
                            if (cellValue == null) {
                                cellValue = "";
                            }
                            setExcelCellText2007(cell, cellValue.toString(),cellStyle);
                        }
                    }
                }
                 //每当行数达到设置的值就刷新数据到硬盘,以清理内存
                if(i%FLUSH_ROWS==0){
                   ((SXSSFSheet)sheet).flushRows();
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 07、10办公版EXCEL导出（直接以流的方式，写到客户端，导出的EXCEL文件只有一个）
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param maxSheetSize    SHEET页的条数
     * @param outputStream    客户端输出流
     * @throws Exception
     */
    public static <T> void beans2excelFile07(List<String> listName,List<T> beans, OutputStream outputStream) throws Exception{
        if ((listName == null) || (listName.size() == 0)) {
            throw new Exception("listName is null when create excel document");
        }
        if (outputStream == null) {
            throw new Exception("outputStream is null when create excel document");
        }
        Workbook wb =new SXSSFWorkbook();
        beans2excelFile07(listName, beans, null, null, MAX_SHEET_SIZE, outputStream);
        try {
            wb.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new Exception(e);
        } 
    }
    /**
     * 07、10办公版EXCEL导出（直接以流的方式，写到客户端，导出的EXCEL文件只有一个）
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param maxSheetSize    SHEET页的条数
     * @param outputStream    客户端输出流
     * @throws Exception
     */
    public static <T> void beans2excelFile07(List<String> listName,List<T> beans,HashMap<String,HashMap<String,String>> result,String sheetName,Integer maxSheetSize, OutputStream outputStream) throws Exception{
        if ((listName == null) || (listName.size() == 0)) {
            throw new Exception("listName is null when create excel document");
        }
        if (outputStream == null) {
            throw new Exception("outputStream is null when create excel document");
        }
        if(maxSheetSize==null || maxSheetSize<=0){
            maxSheetSize=MAX_SHEET_SIZE;
        }
        if(sheetName==null || "".equals(sheetName.trim())){
            sheetName="Sheet";
        }
        Workbook wb =new SXSSFWorkbook();
        if(maxSheetSize==null || maxSheetSize<=0){
            maxSheetSize=MAX_SHEET_SIZE;
        }
        buildExcelDocument2007(wb, listName, beans,result,sheetName,maxSheetSize);
        try {
            wb.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new Exception(e);
        } 
    }
    /**
     * 
     * @param listName
     * @param beans
     * @param response
     * @param fileName 导出的文件名称
     * @throws Exception
     */
    public static <T> void beans2excelFile07(List<String> listName, List<T> beans, HttpServletResponse response,String fileName) throws Exception {
        response.reset();
        response.setContentType("octets/stream");
        response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(fileName, "UTF-8"));
        if ((listName == null) || (listName.size() == 0)) {
            throw new Exception("listName is null when create excel document");
        }
        if (response.getOutputStream() == null) {
            throw new Exception("outputStream is null when create excel document");
        }
        beans2excelFile07(listName, beans, null, null, MAX_SHEET_SIZE, response.getOutputStream());
    }
    /**
     * 07、10办公版EXCEL导出，EXCEL写入数据
     * @param wb            EXCEL工作薄
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param maxSheetSize    SHEET页的条数
     * @throws Exception 
     */
    private static <T> void buildExcelDocument2007(Workbook wb, List<String> listName, List<T> beans,HashMap<String,HashMap<String,String>> result,String sheetName,Integer maxSheetSize) throws Exception
    {
        try
        {
            CellStyle cellStyle=POIUtils.getCellStyleFont(wb,null);
            List titles = new ArrayList();
            List beanAttrNames = new ArrayList();
            List widthList = new ArrayList();
            HashMap<String,String> dateMap=new HashMap<String, String>();
            String[] header = new String[listName.size()];
            int rows_max = 0;//标题占多少列
            List flagList=new ArrayList();
            boolean flagListExists=false;
            for (int i=0;i<listName.size();i++)
            {
                String[] str=listName.get(i).split("&");
                String en_name=str[0];
                String zh_name=str[1];
                beanAttrNames.add(i,en_name);
                titles.add(i, zh_name);
                header[i]=zh_name;
                if (zh_name.split("_").length > rows_max) {
                    rows_max = zh_name.split("_").length;
                }
                if(str.length>2){
                    String flag=str[2];
                    flagList.add(i,flag);
                    if(!flagListExists)
                        flagListExists=true;
                }
                if(str.length>3){
                    widthList.add(str[3]);
                }
                if(str.length>4){
                    dateMap.put(en_name, str[4]);
                }
            }

            PropertyDescriptor[] props = null;

            int size=beans==null?0:beans.size();
            Sheet sheet=null;
            
            //如果没有数据，导出表头
            if(size==0){
                sheet=ExcelHeadUtils.getExcelHead2007(wb, header, sheetName);
                sheet.setDefaultRowHeight((short)350);//高度
                setColumnWidth2007(widthList, sheet,beanAttrNames.size());
                return ;
            }
            
            for (int i = 0; i < size ; i++) {
                int remainder=i%maxSheetSize;
                if(size==0 || i==0 || remainder==0){
                    sheet=ExcelHeadUtils.getExcelHead2007(wb, header,sheetName+(i/maxSheetSize));
                    sheet.createFreezePane( 1, rows_max, 1, rows_max);
                    sheet.setDefaultRowHeight((short)350);//高度
                    setColumnWidth2007(widthList, sheet,beanAttrNames.size());
                }
                if(props==null)
                    props=Introspector.getBeanInfo(beans.get(0).getClass()).getPropertyDescriptors();
                Object bean = beans.get(i);
                Row row = sheet.createRow(remainder+rows_max);
                for (int j = 0; j < beanAttrNames.size(); j++) {
                    String beanAttrName = (String)beanAttrNames.get(j);
                    String flag="";
                    if(flagListExists)
                        flag=(String)flagList.get(j);
                    for (int k = 0; k < props.length; k++) {
                        String propName = props[k].getName();
                        if (propName.equals(beanAttrName))
                        {
                            String pattern=dateMap.get(beanAttrName);
                            Cell cell = row.createCell((short)j);

                            Object cellValue = callGetter(bean, props[k],pattern);
                            if("true".equalsIgnoreCase(flag)){
                                if(result!=null){
                                    HashMap<String,String> hash=result.get(beanAttrName);
                                    if(hash!=null)
                                        cellValue=hash.get(cellValue);
                                }
                            }
                            if (cellValue == null) {
                                cellValue = "";
                            }
                            setExcelCellText2007(cell, cellValue.toString(),cellStyle);
                        }
                    }
                }
                 //每当行数达到设置的值就刷新数据到硬盘,以清理内存
                if(i%FLUSH_ROWS==0){
                   ((SXSSFSheet)sheet).flushRows();
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    /**
     * 07、10办公版EXCEL导出（直接以流的方式，写到客户端，导出的EXCEL文件只有一个）
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param maxSheetSize    SHEET页的条数
     * @param outputStream    客户端输出流
     * @throws Exception
     */
    public static <T> void beans2excelFile07List(List<ArrayList<String>> listColumnName,List<List> list2beans,HashMap<String,HashMap<String,HashMap<String,String>>> result,List<String> listSheetName, OutputStream outputStream) throws Exception{
        if ((listColumnName == null) || (listColumnName.size() == 0)) {
            throw new Exception("listColumnName is null when create excel document");
        }
        if (list2beans.size() != listColumnName.size()) {
            throw new Exception("list2beans and listColumnName size Unequal");
        }
        if (outputStream == null) {
            throw new Exception("outputStream is null when create excel document");
        }
        Workbook wb =new SXSSFWorkbook();
        buildExcelDocument2007List(wb, listColumnName, list2beans, result, listSheetName);
        try {
            wb.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new Exception(e);
        } 
    }
    /**
     * 07、10办公版EXCEL导出，EXCEL写入数据
     * @param wb            EXCEL工作薄
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param maxSheetSize    SHEET页的条数
     * @throws Exception 
     */
    private static <T> void buildExcelDocument2007List(Workbook wb, List<ArrayList<String>> listColumnName,List<List> list2beans,HashMap<String,HashMap<String,HashMap<String,String>>> resultMap,List<String> listSheetName) throws Exception
    {
        try
        {
            int sheets=listColumnName.size();
            boolean sheetNameIsNullFlag=false;
            if(listSheetName==null || listSheetName.size()!=sheets){
                sheetNameIsNullFlag=true;
            }
            for (int s = 0; s < sheets; s++) {
                String sheetName="Sheet"+s;
                if(!sheetNameIsNullFlag){
                    sheetName=listSheetName.get(s);
                }
                List<String> listName=listColumnName.get(s);
                CellStyle cellStyle=POIUtils.getCellStyleFont(wb,null);
                List titles = new ArrayList();
                List beanAttrNames = new ArrayList();
                List widthList = new ArrayList();
                HashMap<String,String> dateMap=new HashMap<String, String>();
                String[] header = new String[listName.size()];
                int rows_max = 0;//标题占多少列
                List flagList=new ArrayList();
                boolean flagListExists=false;
                for (int i=0;i<listName.size();i++)
                {
                    String[] str=listName.get(i).split("&");
                    String en_name=str[0];
                    String zh_name=str[1];
                    beanAttrNames.add(i,en_name);
                    titles.add(i, zh_name);
                    header[i]=zh_name;
                    if (zh_name.split("_").length > rows_max) {
                        rows_max = zh_name.split("_").length;
                    }
                    if(str.length>2){
                        String flag=str[2];
                        flagList.add(i,flag);
                        if(!flagListExists)
                            flagListExists=true;
                    }
                    if(str.length>3){
                        widthList.add(str[3]);
                    }
                    if(str.length>4){
                        dateMap.put(en_name, str[4]);
                    }
                }
    
                PropertyDescriptor[] props = null;
                ArrayList<T> beans=(ArrayList<T>)list2beans.get(s);
                int size=beans==null?0:beans.size();
                Sheet sheet=null;
                
                //如果没有数据，导出表头
                if(size==0){
                    sheet=ExcelHeadUtils.getExcelHead2007(wb, header, sheetName);
                    sheet.setDefaultRowHeight((short)350);//高度
                    setColumnWidth2007(widthList, sheet,beanAttrNames.size());
                    return ;
                }
                
                HashMap<String,HashMap<String,String>> result=null;
                if(resultMap!=null){
                    result=resultMap.get(sheetName);
                }
                sheet=ExcelHeadUtils.getExcelHead2007(wb, header,sheetName);
                sheet.createFreezePane( 1, rows_max, 1, rows_max);
                sheet.setDefaultRowHeight((short)350);//高度
                setColumnWidth2007(widthList, sheet,beanAttrNames.size());
                for (int i = 0; i < size ; i++) {
                    if(props==null)
                        props=Introspector.getBeanInfo(beans.get(0).getClass()).getPropertyDescriptors();
                    Object bean = beans.get(i);
                    Row row = sheet.createRow(rows_max+i);
                    for (int j = 0; j < beanAttrNames.size(); j++) {
                        String beanAttrName = (String)beanAttrNames.get(j);
                        String flag="";
                        if(flagListExists)
                            flag=(String)flagList.get(j);
                        for (int k = 0; k < props.length; k++) {
                            String propName = props[k].getName();
                            if (propName.equals(beanAttrName))
                            {
                                String pattern=dateMap.get(beanAttrName);
                                Cell cell = row.createCell((short)j);
    
                                Object cellValue = callGetter(bean, props[k],pattern);
                                if("true".equalsIgnoreCase(flag)){
                                    if(result!=null){
                                        HashMap<String,String> hash=result.get(beanAttrName);
                                        if(hash!=null)
                                            cellValue=hash.get(cellValue);
                                    }
                                }
                                if (cellValue == null) {
                                    cellValue = "";
                                }
                                setExcelCellText2007(cell, cellValue.toString(),cellStyle);
                            }
                        }
                    }
                     //每当行数达到设置的值就刷新数据到硬盘,以清理内存
                    if(i%FLUSH_ROWS==0){
                       ((SXSSFSheet)sheet).flushRows();
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    /**
     * 07、10办公版EXCEL导出，单元格设置
     * @param cell        单元格对象
     * @param text        单元格文本内容
     * @param cellStyle    单元格格式
     */
    private static void setExcelCellText2007(Cell cell, Object text,CellStyle cellStyle)
    {
        cell.setCellValue(text.toString());
        cell.setCellType(1);//单元格类型
        cell.setCellStyle(cellStyle);
    }
    
    /**
     * 07、10办公版EXCEL导出，单元格宽度设置
     * @param widthList        列宽集合
     * @param sheet            sheet对象
     * @param allSize        总列数
     */
    private static void setColumnWidth2007(List widthList,Sheet sheet,int allSize){
        if(widthList!=null && widthList.size()>0){
            int size=widthList.size();
            for (int i = 0; i < size; i++) {
                try {
                    Integer width=Integer.parseInt((String) widthList.get(i));
                    sheet.setColumnWidth((short) i,width*256);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }else{
            for (int i = 0; i < allSize; i++) {
                try {
                    sheet.setColumnWidth((short) i,COLUMN_WIDTH_WORD*256);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        
        }
    }
    
    /**
     * 03、WPS：EXCEL导出（数据直接写到服务器的EXCEL里，以下载的形式，下载导出的数据）
     * @param listName            列表头名称
     * @param beans                实体集合
     * @param result            数字字典Map集
     * @param filePath            服务器存放文件路径
     * @param fileName            文件名称
     * @param totalSize            EXCEL条数量
     * @param maxSheetSize        sheet页条数量
     * @return List<File>        文件集合
     * @throws Exception
     */
    public static <T> List<File> beans2excelFile03(List<String> listName,List<T> beans,HashMap<String,HashMap<String,String>> result,String filePath,String fileName,Integer totalSize,Integer maxSheetSize) throws Exception{
        if(totalSize==null || totalSize<=0)
            totalSize=TOTAL_SIZE;
        if(maxSheetSize==null || maxSheetSize<=0)
            maxSheetSize=MAX_SHEET_SIZE;
        if(fileName==null)
            fileName="";
        return beans2excelFile2003(listName, beans, result, filePath, fileName,totalSize,maxSheetSize);
    }
    /**
     * 03、WPS：EXCEL导出（数据直接写到服务器的EXCEL里，以下载的形式，下载导出的数据）
     * @param listName            列表头名称
     * @param beans                实体集合
     * @param result            数字字典Map集
     * @param filePath            服务器存放文件路径
     * @param fileName            文件名称
     * @param totalSize            EXCEL条数量
     * @param maxSheetSize        sheet页条数量
     * @param request        客户端请求对象
     * @param response        客户端响应对象
     * @throws Exception
     */
    public static <T> void beans2excelFile03(List<String> listName,List<T> beans,HashMap<String,HashMap<String,String>> result,String filePath,String fileName,Integer totalSize,Integer maxSheetSize,HttpServletRequest request,HttpServletResponse response) throws Exception{
        if(totalSize==null || totalSize<=0)
            totalSize=TOTAL_SIZE;
        if(maxSheetSize==null || maxSheetSize<=0)
            maxSheetSize=MAX_SHEET_SIZE;
        if(fileName==null)
            fileName="";
        List<File> files=beans2excelFile2003(listName, beans, result, filePath, fileName,totalSize,maxSheetSize);
        DownLoadUtils.downLoadFiles(files, filePath, request, response);
    }
    /**
     *  03、WPS：EXCEL导出，每个EXCEL组织数据
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param result        数字字典Map集
     * @param filePath        服务器存放文件路径
     * @param fileName        文件名称
     * @param totalSize        EXCEL条数量
     * @param maxSheetSize    sheet页条数量
     * @return                文件集合
     * @throws Exception
     */
    private static <T> List<File> beans2excelFile2003(List<String> listName,List<T> beans,HashMap<String,HashMap<String,String>> result,String filePath,String fileName,Integer totalSize,Integer maxSheetSize) throws Exception{
        if ((listName == null) || (listName.size() == 0)) {
            throw new Exception("listName is null when create excel document");
        }
        List<File> listFile=new ArrayList<File>();//返回的文件集合
        
        int size=beans==null?0:beans.size();
        String fileSuffixName=".xls";//后缀
        String path="";//文件路径
        Integer startIdx=0;//数据读取的起始行
        Integer endIdx=0;//数据读取的结束行
        (new File(filePath)).mkdirs(); //没有该目录创建目录
        if(size==0){
            startIdx=0;
            endIdx=(totalSize)>size?size:(totalSize);
            String name=fileName+"_第0-0条数据";
            path=filePath+File.separatorChar+name+fileSuffixName;
            HSSFWorkbook wb =new HSSFWorkbook();
            buildExcelDocument2003(wb, listName, beans,result,startIdx,endIdx,maxSheetSize);
            //没有文件，创建文件
            File file = new File(path);
            if (!file.exists()){  
                file.createNewFile();  
            }
            FileOutputStream out=new FileOutputStream(file);
            wb.write(out);
            out.close();
            return listFile;
        }
        for (int i = 0; i < size;i++) {
            int remainder=i%totalSize;
            if(size==0 || remainder==0){
                startIdx=i;
                endIdx=(i+totalSize)>size?size:(i+totalSize);
                String name=fileName+"_第"+(startIdx+1)+"-"+(endIdx)+"条数据";
                path=filePath+"/"+name+fileSuffixName;
                HSSFWorkbook wb =new HSSFWorkbook();
                buildExcelDocument2003(wb, listName, beans,result,startIdx,endIdx,maxSheetSize);
                //没有文件，创建文件
                File file = new File(path);
                if (!file.exists()){  
                    file.createNewFile();  
                }
                FileOutputStream out=new FileOutputStream(file);
                wb.write(out);
                out.close();
                listFile.add(file);
            }else if((size-i)<totalSize && i>endIdx){//最后，不满一万条
                startIdx=i;
                endIdx=i+totalSize;
                String name=fileName+"_第"+(startIdx+1)+"-"+(endIdx)+"条数据";
                path=filePath+name+"."+fileSuffixName;//没有文件，创建文件
                HSSFWorkbook wb =new HSSFWorkbook();
                buildExcelDocument2003(wb, listName, beans, result,startIdx,endIdx,maxSheetSize);
                //没有文件，创建文件
                File file = new File(path);
                if (!file.exists()){  
                    file.createNewFile();  
                }
                FileOutputStream out=new FileOutputStream(file);
                wb.write(out);
                out.close();
                listFile.add(file);
            }
        }
        return listFile;
    }
    
    /**
     * 03,WPS：EXCEL导出，每个EXCEL写入数据
     * @param wb            EXCEL工作薄
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param result        数字字典Map集
     * @param startIdx        数据集合，开始行
     * @param endIdx        数据集合，结束始行
     * @param maxSheetSize    SHEET页条数
     * @throws Exception 
     */
    private static <T> void buildExcelDocument2003(HSSFWorkbook wb, List<String> listName, List<T> beans,HashMap<String,HashMap<String,String>> result,Integer startIdx,Integer endIdx,Integer maxSheetSize) throws Exception
    {
        int totalSize=endIdx-startIdx;//总共条数
        try
        {
            CellStyle cellStyle=POIUtils.getCellStyleFont(wb,null);
            List titles = new ArrayList();
            List beanAttrNames = new ArrayList();
            List widthList=new ArrayList();
            String[] header = new String[listName.size()];
            List flagList=new ArrayList();
            boolean flagListExists=false;
            int rows_max = 0;//标题占多少列
            HashMap<String,String> dateMap=new HashMap<String, String>();
            for (int i=0;i<listName.size();i++)
            {
                String[] str=listName.get(i).split("&");
                String en_name=str[0];
                String zh_name=str[1];
                beanAttrNames.add(i,en_name);
                titles.add(i, zh_name);
                header[i]=zh_name;
                if (zh_name.split("_").length > rows_max) {
                    rows_max = zh_name.split("_").length;
                }
                if(str.length>2){
                    String flag=str[2];
                    flagList.add(i,flag);
                    if(!flagListExists)
                        flagListExists=true;
                }
                if(str.length>3){
                    widthList.add(str[3]);
                }
                if(str.length>4){
                    dateMap.put(en_name, str[4]);
                }
            }

            PropertyDescriptor[] props = null;

            int size=endIdx-startIdx;
            HSSFSheet sheet=null;
            
            //如果没有数据，导出表头
            if(size==0){
                sheet=ExcelHeadUtils.getExcelHead2003(wb, header, "Sheet1");
                sheet.setDefaultRowHeight((short)350);//高度
                setColumnWidth2003(widthList, sheet,beanAttrNames.size());
                return ;
            }
            int u=1;//用来创建每个sheet的行
            int h=0;//用来标注每个sheet也得名字：多少行-多少行
            for (int i = startIdx; i < endIdx ; i++) {
                int remainder=h%maxSheetSize;
                if(size==0 || i==startIdx || remainder==0){
                    u=1;
                    int section=(h+maxSheetSize)>totalSize?totalSize:(h+maxSheetSize);
                    sheet=ExcelHeadUtils.getExcelHead2003(wb, header, "第"+(h+1)+"-"+section+"条");
                    sheet.createFreezePane( 1, rows_max, 1, rows_max);
                    sheet.setDefaultRowHeight((short)350);//高度
                    setColumnWidth2003(widthList, sheet,beanAttrNames.size());
                }
                if(props==null)
                    props=Introspector.getBeanInfo(beans.get(0).getClass()).getPropertyDescriptors();
                Object bean = beans.get(i);
                HSSFRow row = sheet.createRow(u+rows_max-1);
                u++;
                h++;
                for (int j = 0; j < beanAttrNames.size(); j++) {
                    String beanAttrName = (String)beanAttrNames.get(j);
                    String flag=null;
                    if(flagListExists)
                        flag=(String)flagList.get(j);
                    for (int k = 0; k < props.length; k++) {
                        String propName = props[k].getName();
                        if (propName.equals(beanAttrName))
                        {
                            String pattern=dateMap.get(beanAttrName);
                            HSSFCell cell = row.createCell((short)j);
                            Object cellValue = callGetter(bean, props[k],pattern);
                            if("true".equalsIgnoreCase(flag)){
                                if(result!=null){
                                    HashMap<String,String> hash=result.get(beanAttrName);
                                    if(hash!=null)
                                        cellValue=hash.get(cellValue);
                                }
                            }
                            if (cellValue == null) {
                                cellValue = "";
                            }
                            setExcelCellText2003(cell, cellValue.toString(),cellStyle);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
    /**
     * 03,WPS：EXCEL导出（直接以流的方式，写到客户端，导出的EXCEL文件只有一个）
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param maxSheetSize    sheet页条数量
     * @param outputStream    客户端输出流
     * @throws Exception
     */
    public static <T> void beans2excelFile03(List<String> listName,List<T> beans,HashMap<String,HashMap<String,String>> result,String sheetName,Integer maxSheetSize, OutputStream outputStream) throws Exception{
        if ((listName == null) || (listName.size() == 0)) {
            throw new Exception("listName is null when create excel document");
        }
        if(maxSheetSize==null || maxSheetSize<=0){
            maxSheetSize=MAX_SHEET_SIZE;
        }
        if(sheetName==null || "".equals(sheetName.trim())){
            sheetName="Sheet";
        }
        HSSFWorkbook wb =new HSSFWorkbook();
        if(maxSheetSize==null || maxSheetSize<=0)
            maxSheetSize=MAX_SHEET_SIZE;
        buildExcelDocument2003(wb, listName, beans,result,sheetName,maxSheetSize);
        try {
            wb.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new Exception(e);
        } 
    }

    /**
     * 03,WPS：EXCEL导出，EXCEL写入数据
     * @param wb            EXCEL工作薄
     * @param listName        列表头名称
     * @param beans            实体集合
     * @param maxSheetSize    sheet页条数量
     * @throws Exception 
     */
    private static <T> void buildExcelDocument2003(HSSFWorkbook wb, List<String> listName, List<T> beans,HashMap<String,HashMap<String,String>> result,String sheetName,Integer maxSheetSize) throws Exception
    {
        try
        {
            CellStyle cellStyle=POIUtils.getCellStyleFont(wb,null);
            List titles = new ArrayList();
            List beanAttrNames = new ArrayList();
            List widthList = new ArrayList();
            HashMap<String,String> dateMap=new HashMap<String, String>();
            String[] header = new String[listName.size()];
            int rows_max = 0;//标题占多少列
            List flagList=new ArrayList();
            boolean flagListExists=false;
            for (int i=0;i<listName.size();i++)
            {
                String[] str=listName.get(i).split("&");
                String en_name=str[0];
                String zh_name=str[1];
                beanAttrNames.add(i,en_name);
                titles.add(i, zh_name);    
                header[i]=zh_name;
                if (zh_name.split("_").length > rows_max) {
                    rows_max = zh_name.split("_").length;
                }
                if(str.length>2){
                    String flag=str[2];
                    flagList.add(i,flag);
                    if(!flagListExists)
                        flagListExists=true;
                }
                if(str.length>3){
                    widthList.add(str[3]);
                }
                if(str.length>4){
                    dateMap.put(en_name, str[4]);
                }
            }
            PropertyDescriptor[] props =null;

            int size=beans==null?0:beans.size();
            HSSFSheet sheet=null;
            //如果没有数据，导出表头
            if(size==0){
                sheet=ExcelHeadUtils.getExcelHead2003(wb, header, sheetName);
                setColumnWidth2003(widthList, sheet,beanAttrNames.size());
                sheet.setDefaultRowHeight((short)350);//高度
                return ;
            }
            for (int i = 0; i < size ; i++) {
                int remainder=i%maxSheetSize;
                if(size==0 || i==0 || remainder==0){
                    sheet=ExcelHeadUtils.getExcelHead2003(wb, header, sheetName+(i/maxSheetSize));
                    sheet.createFreezePane( 1, rows_max, 1, rows_max);
                    setColumnWidth2003(widthList, sheet,beanAttrNames.size());
                    sheet.setDefaultRowHeight((short)350);//高度
                }
                if(props==null)
                    props= Introspector.getBeanInfo(beans.get(0).getClass()).getPropertyDescriptors();
                Object bean = beans.get(i);
                HSSFRow row = sheet.createRow(remainder+rows_max);
                for (int j = 0; j < beanAttrNames.size(); j++) {

                    String beanAttrName = (String)beanAttrNames.get(j);
                    String flag=null;
                    if(flagListExists)
                        flag=(String)flagList.get(j);
                    for (int k = 0; k < props.length; k++) {
                        String propName = props[k].getName();
                        if (propName.equals(beanAttrName))
                        {
                            String pattern=dateMap.get(beanAttrName);
                            HSSFCell cell = row.createCell((short)j);
                            Object cellValue = callGetter(bean, props[k],pattern);
                            if("true".equalsIgnoreCase(flag)){
                                if(result!=null){
                                    HashMap<String,String> hash=result.get(beanAttrName);
                                    if(hash!=null)
                                        cellValue=hash.get(cellValue);
                                }
                            }
                            if (cellValue == null) {
                                cellValue = "";
                            }
                            setExcelCellText2003(cell, cellValue.toString(),cellStyle);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 03,WPS：EXCEL导出，单元格设置
     * @param cell        单元格对象
     * @param text        单元格文本内容
     * @param cellStyle    单元格格式
     */
    private static void setExcelCellText2003(HSSFCell cell, Object text,CellStyle cellStyle)
    {
        cell.setCellValue(text.toString());
        cell.setCellType(1);//单元格类型
        cell.setCellStyle(cellStyle);
    }

    /**
     * 03,WPS：EXCEL导出，单元格宽度设置
     * @param widthList        列宽集合
     * @param sheet            sheet对象
     * @param allSize        总列数
     */
    private static void setColumnWidth2003(List widthList,HSSFSheet sheet,int allSize){
        if(widthList!=null && widthList.size()>0){
            int size=widthList.size();
            for (int i = 0; i < size; i++) {
                try {
                    Integer width=Integer.parseInt((String) widthList.get(i));
                    sheet.setColumnWidth((short) i,width*256);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }else{
            for (int i = 0; i < allSize; i++) {
                try {
                    sheet.setColumnWidth((short) i,COLUMN_WIDTH_WORD*256);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
    }

    /**
     * 根据反射，获取实体属性的值
     * @param target    实体属性
     * @param prop        反射调用类
     * @param pattern    日期格式
     * @return            
     */
    private static Object callGetter(Object target, PropertyDescriptor prop,String pattern) {
        Object o = null;
        if (prop.getReadMethod() != null) {
            try {
                o = prop.getReadMethod().invoke(target, null);
                if (Date.class.equals(prop.getPropertyType())) {
                    if(pattern!=null && !"".equals(pattern)){
                        try {
                            o = new SimpleDateFormat(pattern).format(o);
                        } catch (Exception e) {
                            o = new SimpleDateFormat(PATTERN).format(o);
                        }
                    }else{
                        o = formatDate(o);
                    }
                }
            } catch (Exception e) {
                o = null;
            }
        }
        return o;
    }
    
    /**
     * 日期转换
     * @param date
     * @return 字符串的日期
     */
    private static String formatDate(Object date) {
        if(date==null)
            return "";
        String dateStr = new SimpleDateFormat(DATE_HH_PATTERN).format(date);
        if("00:00:00".equals(dateStr)){
            return new SimpleDateFormat(DATE_PATTERN).format(date);
        }
        return new SimpleDateFormat(PATTERN).format(date);
    }
}
