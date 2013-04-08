
package com.example.sample02;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainFragment extends Fragment implements LoaderCallbacks<Bitmap> {
    @SuppressWarnings("unused")
    public static final String TAG = "MainFragment";

    private static final int LOADER_ID = 1;

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LoaderManager manager = this.getLoaderManager();
        manager.initLoader(LOADER_ID, null, this);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.LoaderManager.LoaderCallbacks#onCreateLoader(int,
     * android.os.Bundle)
     */
    @Override
    public Loader<Bitmap> onCreateLoader(int loaderId, Bundle arguments) {
        Loader<Bitmap> loader = null;
        switch (loaderId) {
            case LOADER_ID:
                loader = new BitmapAsyncTaskLoader(this.getActivity());
                break;
            default:
                break;
        }

        return loader;
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.LoaderManager.LoaderCallbacks#onLoadFinished(android.support.v4.content
     * .Loader, java.lang.Object)
     */
    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
        ImageView imageView = (ImageView) this.getView().findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);

    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.LoaderManager.LoaderCallbacks#onLoaderReset(android.support.v4.content
     * .Loader)
     */
    @Override
    public void onLoaderReset(Loader<Bitmap> arg0) {
        // TODO Auto-generated method stub

    }
}
