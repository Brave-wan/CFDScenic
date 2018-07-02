package com.pubfin.tools;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/25 0025.
 */
public class ErgodicFile {
    /**
     * 遍历路径下文件夹与文件
     * @param filePath
     * @return
     */
    public List<HashMap<String, String>> getFiles(File filePath)
    {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        File files[] = filePath.listFiles();
        if(files != null){
            for (File f : files){
                if(f.isDirectory()){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("filePath", f.getPath());//文件夹路径
                    map.put("fileName", f.getName());//文件夹名称
                    map.put("file", "文件夹");//文件夹标识
                    list.add(map);
                }else{
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("filePath", f.getPath());//文件路径
                    map.put("fileName", f.getName());//文件名称
                    map.put("file", "文件");//文件标识
                    list.add(map);
                }
            }
        }
        return list;
    }
}
