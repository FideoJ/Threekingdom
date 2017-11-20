package com.pear.threekingdom.db;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

public class DbBitmapUtility {

  public static byte[] img2Bytes(Bitmap bitmap) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(CompressFormat.PNG, 0, stream);
    return stream.toByteArray();
  }

  public static Bitmap bytes2Img(byte[] image) {
    return BitmapFactory.decodeByteArray(image, 0, image.length);
  }
}