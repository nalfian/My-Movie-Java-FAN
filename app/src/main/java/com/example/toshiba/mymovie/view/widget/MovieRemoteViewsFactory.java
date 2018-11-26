package com.example.toshiba.mymovie.view.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.toshiba.mymovie.R;
import com.example.toshiba.mymovie.model.ResultsItem;

import java.util.concurrent.ExecutionException;

import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.CONTENT_URI;


public class MovieRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private Cursor cursor;
    private Context mContext;

    public MovieRemoteViewsFactory(Context context) {
        mContext = context;

    }

    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }


    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    private ResultsItem getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new ResultsItem(cursor);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        ResultsItem item = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_movie_widget);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w500"+item.getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

            rv.setImageViewBitmap(R.id.img_widget, bitmap);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        rv.setTextViewText(R.id.tv_movie_title_widget, item.getTitle());
        Bundle extras = new Bundle();
        extras.putInt(MovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.img_widget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
