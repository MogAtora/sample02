/*
 * Copyright 2013 peko<golden.slumbers.99@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sample02;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.support.v4.content.AsyncTaskLoader;

/**
 * 
 */
public class BitmapAsyncTaskLoader extends AsyncTaskLoader<Bitmap> {

    @SuppressWarnings("unused")
    public static final String TAG = "BitmapAsyncTaskLoader";

    private static final String FILE_NAME = "huge_image.jpg";

    public BitmapAsyncTaskLoader(Context context) {
        super(context);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.content.Loader#onStartLoading()
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        this.forceLoad();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.content.AsyncTaskLoader#loadInBackground()
     */
    @Override
    public Bitmap loadInBackground() {
        Context context = this.getContext();
        Resources resources = context.getResources();
        AssetManager manager = resources.getAssets();
        InputStream inputStreamForDecodeBounds = null;
        InputStream inputStream = null;
        try {
            // 基本は一緒
            // inJustDecodeBounds = true で読み込んで画像サイズを取得する
            // options.outWidth, options.outHeight にサイズが入る
            inputStreamForDecodeBounds = manager.open(FILE_NAME);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStreamForDecodeBounds, null, options);
            int bitmapWidth = options.outWidth;
            int bitmapHeight = options.outHeight;

            // 0 <= width <= bitmapWidth
            // 0 <= height <= bitmapHeight
            // でくり抜ける
            // InputStream は使い回しできないので作り直し!!
            inputStream = manager.open(FILE_NAME);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStream, true);
            // Rect rect = new Rect(0, 0, 200, 200); // 左上から 200 * 200 で切り出す。
            Rect rect = new Rect(150, 150, 400, 400); // 左上 (50, 100)から 400 * 400 で切り出す。
            // 右下100 * 100 を切り出す
            // Rect rect = new Rect(bitmapWidth - 200, bitmapHeight - 200, bitmapWidth,
            // bitmapHeight);

            // Rect rect = new Rect(0, 0, bitmapWidth, bitmapHeight); // 全読み

            // inJustDecodeBounds = false; にして実際に読み込む
            options.inJustDecodeBounds = false;
            Bitmap bitmap = decoder.decodeRegion(rect, options);

            return bitmap;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {

                }
            }

            if (inputStreamForDecodeBounds != null) {
                try {
                    inputStreamForDecodeBounds.close();
                } catch (IOException e2) {

                }
            }
        }

        return null;
    }
}
