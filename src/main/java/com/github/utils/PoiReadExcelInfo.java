package com.github.utils;

import com.github.model.CardLoseInfo;
import org.apache.http.entity.ContentType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PoiReadExcelInfo {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void checkExcelVaild(MultipartFile file) throws Exception {
        if (file == null){
            throw new Exception("文件不存在");
        }
        if (!((file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是Excel");
        }

    }

    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                //如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue=sdf.format(HSSFDateUtil.getJavaDate(cell.
                            getNumericCellValue())).toString();
                    break;
                } else {
                    //把数字当成String来读，避免出现1读成1.0的情况
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(cell.getStringCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    public static List<CardLoseInfo> readExcel(MultipartFile file) throws Exception {
        List<CardLoseInfo> cardLoseInfoList = new ArrayList<CardLoseInfo>();
        try {
            checkExcelVaild(file);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        //文件流
        InputStream is = file.getInputStream();
        Workbook workbook = null;
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003
            workbook = new HSSFWorkbook(is);
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010
            workbook = new XSSFWorkbook(is);
        }
        //获取Excel文件第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        int count = 0;
        //遍历每一行数据
        for (Row row : sheet){
            //过滤前4行的数据
            if (count<3){
                count++;
                continue;
            }
            CardLoseInfo cardLoseInfo = new CardLoseInfo();
            //遍历每一列数据
            int c = 0;
            for (Cell cell : row){
                String value = getCellValue(cell);
                switch (c){
                    case 0:
                        cardLoseInfo.setSt01(value);
                        break;
                    case 1:
                        cardLoseInfo.setSt02(value);
                        break;
                    case 2:
                        cardLoseInfo.setSt03(value);
                        break;
                    case 3:
                        cardLoseInfo.setSt04(value);
                        break;
                    case 4:
                        cardLoseInfo.setSt05(value);
                        break;
                    case 5:
                        cardLoseInfo.setSt06(value);
                        break;
                    case 6:
                        cardLoseInfo.setSt07(value);
                        break;
                    case 7:
                        cardLoseInfo.setSt08(value);
                        break;
                    case 8:
                        cardLoseInfo.setSt09(value);
                        break;
                    case 9:
                        cardLoseInfo.setSt10(value);
                        break;
                    case 10:
                        cardLoseInfo.setSt11(value);
                        break;
                    case 11:
                        cardLoseInfo.setSt12(value);
                        break;
                    case 12:
                        cardLoseInfo.setSt13(value);
                        break;
                    case 13:
                        cardLoseInfo.setSt14(value);
                        break;
                    case 14:
                        cardLoseInfo.setSt15(value);
                        break;
                    case 15:
                        cardLoseInfo.setSt16(value);
                        break;
                }
                c++;
            }
            cardLoseInfoList.add(cardLoseInfo);
        }
        return cardLoseInfoList;
    }

    public static List<CardLoseInfo> Res(){
        List<CardLoseInfo> cardLoseInfoList = null;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入文件绝对路径(如:E:/解析规则.xlsx)：");
            File file = new File(scanner.nextLine());

            FileInputStream inputStream = new FileInputStream(file);
            //File 转 MultipartFile
            MultipartFile mMultipartFile = new MockMultipartFile(file.getName(),file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(),inputStream);

            cardLoseInfoList = readExcel(mMultipartFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardLoseInfoList;
    }

    public static void main(String[] args){

        List<CardLoseInfo> cardLoseInfoList = Res();
    }
}
