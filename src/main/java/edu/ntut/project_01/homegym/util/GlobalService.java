package edu.ntut.project_01.homegym.util;

import lombok.extern.slf4j.Slf4j;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import java.io.*;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class GlobalService {

    public static byte[] hgImg(){
        String logo_09 = "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";
        return Base64.getDecoder().decode(logo_09);
    }

    //寫進資料夾的方法
    public static String imageSaveToFile(String data, File folder,Integer id,String evt) {
        System.out.println("準備寫進資料夾==============================================");
        String s = "static";
        int index = s.length();
        //取名用
        int start = folder.toString().lastIndexOf("static");
        String folderPath= folder.toString().substring(start+index);
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 4);

        //base64轉byte陣列
        String dataToBase64 = data.substring(data.indexOf(",") + 1);
        byte[] bytes = Base64.getDecoder().decode(dataToBase64);

//        String name = folder.toString().substring(start+index+1)+id.toString()+uuid;
        String name = folder.toString().substring(start+index+1)+id.toString();
        System.out.println("filename======================================================"+name);
        File file = new File(folder,name+evt);
        System.out.println("file.toString()===================================="+file.toString());
        try {
            OutputStream out = new FileOutputStream(file);
            out.write(bytes);
            System.out.println("讀取完畢");
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("失敗");
        }
        System.out.println("存到資料庫的名字=============="+folderPath+"\\"+name+evt);
        return folderPath+"\\"+name+evt;
    }
}
