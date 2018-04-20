package jowney.com.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Jowney
 * @time 2017-10-26  13:43
 * @describe
 */
public class BitmapUtils {


        public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            System.out.println("angle2=" + angle);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap
                    .getHeight(), matrix, true);
            return resizedBitmap;
        }

        // 返回裁剪后的Bitmap
        public static Bitmap crop(Bitmap bm, int left, int top, int right, int bottom) {
            Rect r = new Rect(left, top, right, bottom);
            int width = r.width(); // CR: final == happy panda!
            int height = r.height();
            Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            {
                Canvas canvas = new Canvas(croppedImage);
                Rect dstRect = new Rect(0, 0, width, height);
                canvas.drawBitmap(bm, r, dstRect, null);
            }
            return croppedImage;
        }

        public static Bitmap getBitmapFromPath(String path) {

            if (!new File(path).exists()) {
                System.err.println("getBitmapFromPath: file not exists");
                return null;
            }
            // Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
            // Canvas canvas = new Canvas(bitmap);
            // Movie movie = Movie.decodeFile(path);
            // movie.draw(canvas, 0, 0);
            //
            // return bitmap;

            byte[] buf = new byte[1024 * 1024];// 1M
            Bitmap bitmap = null;

            try {

                FileInputStream fis = new FileInputStream(path);
                int len = fis.read(buf, 0, buf.length);
                bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
                if (bitmap == null) {
                    System.out.println("len= " + len);
                    System.err.println("path: " + path + "  could not be decode!!!");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

            return bitmap;
        }


        public static Bitmap copyBitmap(Bitmap bitmap) {
            return Bitmap.createBitmap(bitmap);
        }

        public static String saveBitmap(Bitmap bitmap, String path,String fileName) {
            if (bitmap == null)
                return "";

            File folder = new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            File jpgFile = new File(path + "/"+fileName +".jpg");
            if (jpgFile.exists())
                jpgFile.delete();

            try {
                FileOutputStream out = new FileOutputStream(jpgFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                return path;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        public static byte[] bitmapToArray(Bitmap bitmap) {
            byte[] bytes = new byte[0];
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                bytes = baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();

            }
            return bytes;
        }
    }


