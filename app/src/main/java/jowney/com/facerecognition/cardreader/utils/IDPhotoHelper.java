package jowney.com.facerecognition.cardreader.utils;

import android.graphics.Bitmap;

import com.zkteco.android.IDReader.WLTService;

/**
 * @author Jowney
 * @time 2017-10-31  16:13
 * @describe
 */
public class IDPhotoHelper {

	/*
	 * private void renderRgbFrame(Bitmap bitmap, VpxOutputBuffer outputBuffer,
	 * boolean scale) { if (bitmap == null || bitmap.getWidth() !=
	 * outputBuffer.width || bitmap.getHeight() != outputBuffer.height) { bitmap
	 * = Bitmap.createBitmap(outputBuffer.width, outputBuffer.height,
	 * Bitmap.Config.RGB_565); // bitmap =
	 * Bitmap.createBitmap(outputBuffer.width, // outputBuffer.height,
	 * Bitmap.Config.ARGB_8888); } // TODO convert outputBuffer.data to
	 * ARGB_8888,so that support alpha // channel
	 * bitmap.copyPixelsFromBuffer(outputBuffer.data); Canvas canvas =
	 * surface.lockCanvas(null); if (scale) { canvas.scale(((float)
	 * canvas.getWidth()) / outputBuffer.width, ((float) canvas.getHeight()) /
	 * outputBuffer.height); } canvas.drawBitmap(bitmap, 0, 0, null);
	 * surface.unlockCanvasAndPost(canvas); }
	 */

    public static Bitmap Bgr2Bitmap(byte[] bgrbuf)
    {
        int width = WLTService.imgWidth;
        int height = WLTService.imgHeight;
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // RGB_565
        int row = 0, col = 0;
        for (int i = bgrbuf.length-1; i >= 3; i -= 3) {
            int color = bgrbuf[i] & 0xFF;
            color += (bgrbuf[i-1] << 8) & 0xFF00;
            color += ((bgrbuf[i-2]) << 16) & 0xFF0000;
            bmp.setPixel(col++, row, color);
            if (col == width) {
                col = 0;
                row++;
            }
        }
        return bmp;
    }


    public static Bitmap Bgr2ARGB(byte[] bgrbuf) {

        int width = WLTService.imgWidth;
        int height = WLTService.imgHeight;
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // ARGB_8888
        int row = 0, col = 0;
        for (int i = bgrbuf.length - 1; i >= 3; i -= 3) {
            int color = 0xFF << 24;
            color += (bgrbuf[i]) & 0xFF;
            color += (bgrbuf[i - 1] << 8) & 0xFF00;
            color += ((bgrbuf[i - 2]) << 16) & 0xFF0000;
            bmp.setPixel(col++, row, color);
            if (col == width) {
                col = 0;
                row++;
            }
        }
        return bmp;
    }

    public static Bitmap Bgr2ARGB_Mirror(byte[] bgrbuf) {

        int width = WLTService.imgWidth;
        int height = WLTService.imgHeight;
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] rowPixel = new int[width]; // 通过位图的大小创建像素点数组
        // ARGB_8888
        int row = 0, col = 0;
        for (int i = bgrbuf.length - 1; i >= 3; i -= 3) {
            int color = 0xFF << 24;
            color += (bgrbuf[i]) & 0xFF;
            color += (bgrbuf[i - 1] << 8) & 0xFF00;
            color += ((bgrbuf[i - 2]) << 16) & 0xFF0000;
            rowPixel[col++] = color;

            if (col == width) {
                // 左右镜像交换镜像
                int temp = 0;
                for (int j = 0; j < width / 2; j++) {
                    temp = rowPixel[j];
                    rowPixel[j] = rowPixel[width - j-1];
                    rowPixel[width - j-1] = temp;
                }
                for (int k = 0; k < width; k++) {
                    bmp.setPixel(k, row, rowPixel[k]);
                }

                col = 0;
                row++;
            }

        }
        return bmp;
    }
}
