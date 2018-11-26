package id.co.gitsolution.mymoviefav.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_MOVIE = "favorite";

    public static final class MovieColumns implements BaseColumns {
        public static String ID = "_id";
        public static String ID_MOVIE = "id";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String REALESE_DATE = "release_date";
        public static String POSTER_PATH = "poster_path";

        public static final String AUTHORITY = "com.example.toshiba.mymovie";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }
}
