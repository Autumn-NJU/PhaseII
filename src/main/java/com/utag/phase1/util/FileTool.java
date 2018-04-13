package com.utag.phase1.util;

/**
 * 读写文件的工具类，读文件返回list便于将jsonStr 转化为 java bean
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTool {

    private static final String prefixPath = "src/main/resources/static/data/";

    public static boolean writeFile(String fileName, String content) {

        try {
            String filePath = prefixPath + fileName;

            File file = new File(filePath);
            System.out.println(file.getAbsolutePath());
            FileWriter writer = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write(content);
            printWriter.println();
            writer.close();
            printWriter.close();

            return true;
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return false;
    }


    public static List<String> readFile(String fileName) {
        List<String> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(new File(prefixPath + fileName));
            BufferedReader reader = new BufferedReader(fileReader);
            String str;
            while ((str = reader.readLine()) != null) {
                if(str.startsWith("{"))
                    list.add(str);

            }
            reader.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return list;
    }

    public static boolean rewriteFile(String fileName, ArrayList<String> contentList) {
        try {
            String filePath = prefixPath + fileName;

            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);

            for (String content: contentList){
                printWriter.write(content);
                printWriter.println();
            }
            writer.close();
            printWriter.close();

            return true;
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * 给出固定文件夹下所有文件名的方法
     * @param filePath
     * @return
     */
    public static List<String> listPictureName(String filePath){
        File file = new File(filePath);
        List<String> list = new ArrayList<>();
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i = 0; i < files.length; i++){
                if(files[i].isDirectory()){
                    return listPictureName(files[i].getPath());
                }
                list.add(files[i].getName());
            }
        }
        return list;
    }


}
