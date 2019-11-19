package com.github.test;

import com.github.model.CardLoseInfo;
import com.github.service.impl.CardLoseInfoServiceImpl;
import com.github.utils.FileUtil;
import com.github.utils.PoiReadExcelInfo;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardLoseInfoTest {
    private static ApplicationContext applicationContext = null;

    static{
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testFileUtil() throws SQLException {
        String path = "E:\\2019";
        List<File> fileList = new ArrayList<File>();
        FileUtil.getFileList(path,fileList);
        for (File file:fileList) {
            System.out.println(file.getAbsolutePath());
        }
    }

    @Test
    public void testFileUtilSave() throws Exception {
        CardLoseInfoServiceImpl cardLoseInfoService = (CardLoseInfoServiceImpl)applicationContext.getBean("cardLoseInfoService");
        String path = "E:\\2019";
        List<File> fileList = new ArrayList<File>();
        FileUtil.getFileList(path,fileList);
        for (File file:fileList) {
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            List<CardLoseInfo> cardLoseInfoList = PoiReadExcelInfo.readExcel(multipartFile);
            for (CardLoseInfo cardLoseInfo:cardLoseInfoList) {
                cardLoseInfo.setStType("2019");
                cardLoseInfoService.saveInfo(cardLoseInfo);
            }
            System.out.println(file.getAbsolutePath());
        }
    }
}
