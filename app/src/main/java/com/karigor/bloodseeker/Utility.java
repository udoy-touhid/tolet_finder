package com.karigor.bloodseeker;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by touhid on 2019-10-18.
 * Email: udoy.touhid@gmail.com
 */
public class Utility {

    public static void Toaster(Context context, String str, int lenght){

        int len = lenght==1?Toast.LENGTH_LONG:Toast.LENGTH_SHORT;
        Toast.makeText(context, str, len).show();

    }

    public static void Toaster(Context context, String str){

        int len = Toast.LENGTH_SHORT;
        Toast.makeText(context, str, len).show();

    }
}
