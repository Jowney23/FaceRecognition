package jowney.com.facerecognition.cardreader;

import android.app.Activity;
import android.graphics.Bitmap;

/**
 * @author Jowney
 * @time 2017-10-31  15:09
 * @describe
 */
public interface IIDCardReader {
    int openCardReader();

    int closeCardReader();

    int readCard();

    IdentityCard getInfo();

    int destoryCardReader();

    Bitmap getPhoto();

    void setContext(Activity activity);
}
