package com.github.utils;

import java.io.File;
import java.util.List;

public class FileUtil {

    public static List<File> getFileList(String strPath,List<File> filelist) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath(),filelist); // 获取文件绝对路径
                } else if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) { // 判断文件名是否以.xls结尾
                    String strFileName = files[i].getAbsolutePath();
                    if(!strFileName.contains("盖章登记表") && !strFileName.contains("执法证情况汇总") && !strFileName.contains("执法人员信息 (新证批次)")){//过滤掉盖章登记表4.19.xls类型的文件
                        filelist.add(files[i]);
                    }
                } else {
                    continue;
                }
            }
        }
        return filelist;
    }
}
