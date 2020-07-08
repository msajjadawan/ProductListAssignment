package com.innovidio.androidbootstrap.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.innovidio.androidbootstrap.db.dao.FavoriteItemDao;
import com.innovidio.androidbootstrap.entity.FavoriteItem;
import com.innovidio.androidbootstrap.entity.Feed;
import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.db.converters.IntegerListConverter;
import com.innovidio.androidbootstrap.db.converters.StringListConverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

@Database(
        entities = {
                //@TODO add your Entity classes here
                FavoriteItem.class,
        },
        version = 1,
        exportSchema = false
)
@TypeConverters(
        {
                //TODO add you typeConnverters here
                IntegerListConverter.class, DateConverter.class, StringListConverter.class
        }
)
public abstract class AppDatabase extends RoomDatabase {

    //TODO define you DAO against Entity here
    public abstract FavoriteItemDao getFavoriteItemDao();





    private static final String TAG = "AppDatabase";
    private static AppDatabase instance = null;
    private static SupportSQLiteDatabase sQLiteDatabase = null;
    private static Context context = null;

    private static final Object sLock = new Object();
    public static synchronized AppDatabase getInstance(Context _context) {
        context = _context;
        if (instance == null) {
            synchronized (sLock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AppDatabase.class, "database_name")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addMigrations()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    sQLiteDatabase = db;

                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            populateData();
                                            // instance.getFeedDao().insert(list)
                                        }
                                    });
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            }).build();
                }
            }

        }
        return instance;
    }

    public static void populateData() {

        // for processing direct query
        /*
        StringBuilder sb = new StringBuilder();
        String data = null;
        sb.append("INSERT INTO Mushroom VALUES ");
        data = loadStringDataFromAsset("mushrooms/mushInsert_en");
        sb.append(data);
        sQLiteDatabase.execSQL(sb.toString());
        */

// for processing JSON datat
       /* String json = loadStringDataFromAsset("asset_filename");
        Type type = Types.newParameterizedType(List.class, Breed.class, BreedDetail.class, PuppyName.class);
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<List<Breed>> jsonAdapter = moshi.adapter(type);
        List<Breed> breeds = jsonAdapter.fromJson(json);
        return breeds;

        */

    }

    private static String loadStringDataFromAsset(String fileName) {
        String jsonStr = null;
        try {

            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            jsonStr = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "loadStringDataFromAsset: ", e.getCause());
            return null;
        }
        return jsonStr;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    //    @Override
    //    public void clearAllTables() {
    //
    //    }


    public Boolean clearAndResetAllTables() {
        if (instance == null) {
            return false;
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("DELETE FROM sqlite_sequence");

        instance.beginTransaction();
        try {
            instance.clearAllTables();
            instance.query(query);
            instance.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            instance.endTransaction();
        }
    }


}