package com.utag.phase1.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class UnZipUtil {
    List<String> fileList;
    private static final String OUTPUT_FOLDER = "src/main/resources/static/task/files";
    private static final String PREFIX_PATH = "src/main/resources/static/task/zip/";

    private static final int BUFFER_SIZE = 4096;
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory, int taskId) throws IOException {
        destDirectory  = destDirectory + "/" + taskId;
        File destDir = new File(destDirectory );
        System.out.println(destDir);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public static void main(String[] args) {
        UnZipUtil unZipUtil = new UnZipUtil();
        try {
           UnZipUtil.Unzip("归档.zip", 1);
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }
    }
    public static void Unzip(String zipFileName, int taskId){
        String zipFilePath = PREFIX_PATH + zipFileName;
        String desPath = OUTPUT_FOLDER;
        UnZipUtil unZipUtil = new UnZipUtil();
        try {
            unZipUtil.unzip(zipFilePath, desPath, taskId);
            System.out.println("Unzip...");
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}