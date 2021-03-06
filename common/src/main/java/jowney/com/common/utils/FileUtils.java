package jowney.com.common.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author Jowney
 * @time 2017-10-30  11:55
 * @describe
 */
public class FileUtils {


    public static void makeDir(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        return;
    }
    public static File newFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return new File(path);
    }

    public static String moveFile(String source, String des) {
        File sourceFile = new File(source);
        if (!sourceFile.exists()) {

            return "";
        }
        if (sourceFile.renameTo(newFile(des))) {
            return des;
        }
        return "";

    }

    public static void deleteFile(String path) {
        try {
            new File(path).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param context Context 使用CopyFiles类的Activity
     * @param oldPath String  原文件路径  如：/aa
     * @param newPath String  复制后路径  如：xx:/bb/cc
     */
    public static void copyFilesFassets(Context context, String oldPath, String newPath) {
        try {
            File file = new File(newPath);
           if(file.exists()){
                return;
            }
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {
                //如果是目录
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFassets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
