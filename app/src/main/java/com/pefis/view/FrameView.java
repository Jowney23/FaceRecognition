//package com.pefis.view;
//
//
//import com.gdface.attendancesystem.R;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.util.AttributeSet;
//import android.view.View;
//
//import static android.graphics.Color.BLACK;
//
//public final class FrameView extends View {
//
//
//    private static final int Top = 100;
//    private static final int Bottom = 250;
//    private static final int LF = 80;
//    private static final int changdu = 150;
//    private static final int kuandu = 8;
//
//    /**
//     * 画笔对象的引用
//     */
//    private Paint paint;
//    private final int maskColor;
//    private final int resultColor;
//    private final int mColor_BG;
//    private final int resultPointColor;
//    private int ScreenRate;
//    private static float density;
//
//    public FrameView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        density = context.getResources().getDisplayMetrics().density;
//        // 将像素转换成dp
//        ScreenRate = (int) (20 * density);
//
//        paint = new Paint();
//        Resources resources = getResources();
//        maskColor = resources.getColor(R.color.viewfinder_mask);
//        resultColor = resources.getColor(R.color.result_view);
//        mColor_BG = resources.getColor(R.color.two_dimension_code);
//
//        resultPointColor = resources.getColor(R.color.possible_result_points);
//    }
//
//    @Override
//    public void onDraw(Canvas canvas) {
//        // canvas.drawColor(getResources().getColor(R.color.two_dimension_code));
//
//        // 获取屏幕的宽和高
//        int width = canvas.getWidth();
//        int height = canvas.getHeight();
//
//        // paint.setColor(resultBitmap != null ? resultColor : maskColor);
//        paint.setColor(BLACK);BLACK
//        paint.setAlpha(100);
//
//        // 画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
//        // 扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
//        canvas.drawRect(0, 0, width, Top, paint);
//        canvas.drawRect(0, Top, LF, height, paint);
//        canvas.drawRect(LF, height - Bottom, width, height, paint);
//        canvas.drawRect(width - LF, Top, width, height - Bottom, paint);
//
//        // 画扫描框边上的角，总共8个部分
//        paint.setColor(Color.RED);
//        paint.setAlpha(150);
//        //左上角
//        canvas.drawRect(LF, Top, LF + changdu + kuandu, kuandu + Top, paint);
//        canvas.drawRect(LF, Top + kuandu, LF + kuandu, kuandu + changdu + Top, paint);
//
//        //左下角
//        canvas.drawRect(LF, height - Bottom - changdu - kuandu, LF + kuandu, height - Bottom, paint);
//        canvas.drawRect(LF + kuandu, height - Bottom - kuandu, LF + kuandu + changdu, height - Bottom, paint);
//        //右上角
//        canvas.drawRect(width - LF - changdu - kuandu, Top, width - LF, Top + kuandu, paint);
//        canvas.drawRect(width - LF - kuandu, Top + kuandu, width - LF, Top + kuandu + changdu, paint);
//        //右下角
//        canvas.drawRect(width - LF - changdu - kuandu, height - Bottom - kuandu, width - LF, height - Bottom, paint);
//        canvas.drawRect(width - LF - kuandu, height - Bottom - kuandu - changdu, width - LF, height - Bottom - kuandu, paint);
//
//    }
//
//}
