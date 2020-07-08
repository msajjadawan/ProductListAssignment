package com.innovidio.androidbootstrap;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/*
 * A Singleton for managing your SharedPreferences.
 *
 * You should make sure to change the SETTINGS_NAME to what you want
 * and choose the operating made that suits your needs, the default is
 * MODE_PRIVATE.
 *
 * IMPORTANT: The class is not thread safe. It should work fine in most
 * circumstances since the write and read operations are fast. However
 * if you call edit for bulk updates and do not commit your changes
 * there is a possibility of data loss if a background thread has modified
 * preferences at the same time.
 *
 * Usage:
 *
 * int sampleInt = MyPreferences.getInstance(context).getInt(Key.SAMPLE_INT);
 * MyPreferences.getInstance(context).set(Key.SAMPLE_INT, sampleInt);
 *
 * If MyPreferences.getInstance(Context) has been called once, you can
 * simple use MyPreferences.getInstance() to save some precious line space.
 */
@Singleton
public class AppPreferences {
    // TODO: CHANGE THIS TO SOMETHING MEANINGFUL
    private static final String SETTINGS_NAME = "default_settings";
    private static AppPreferences sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;

    private Map<String, Object> objectMap = new HashMap<>();

    /**
     * Enum representing your setting names or key for your setting.
     */
    public enum Key {
        /* Recommended naming convention:
         * ints, floats, doubles, longs:
         * SAMPLE_NUM or SAMPLE_COUNT or SAMPLE_INT, SAMPLE_LONG etc.
         *
         * boolean: IS_SAMPLE, HAS_SAMPLE, CONTAINS_SAMPLE
         *
         * String: SAMPLE_KEY, SAMPLE_STR or just SAMPLE
         */
        SAMPLE_STR,
        SAMPLE_INT,
        NOTIFICATION_STATUS,
        NOTIFICATION_VIDEO,
        NOTIFICATION_DOCUMENTS,
        NOTIFICATION_AUDIOS,
        NOTIFICATION_LINKS,
        NOTIFICATION_IMAGES,
        NOTIFICATION_DARK_THEME,
        IS_APP_ADS_FREE,
        PREF_VERSION_CODE_KEY,
        IS_APP_AD_FREE,
        BILLING_TYPE,
        IS_CONSENT_SHOWN,
        SOURCE_LANGUAGE,
        TARGET_LANGUAGE,
        TARGET_LANGUAGE_KEY,
        SOURCE_LANGUAGE_KEY,
        IS_IN_APP_DIALOG_SHOWN,
        MAIN_ACTIVITY_TUTORIAL_SHOWN_STR
        ;
    }

    @Inject
    public AppPreferences(Context context) {
        // Read all preferences in a background thread and put them into a map.

        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
        if (mPref != null && mPref.getAll().size() > 0) {

            objectMap.putAll(mPref.getAll());
        }

    }

//    public static AppPreferences getInstance(Context context) {
//        if (sSharedPrefs == null) {
//            sSharedPrefs = new AppPreferences(context.getApplicationContext());
//        }
//        return sSharedPrefs;
//    }
//
//    public static AppPreferences getInstance() {
//        if (sSharedPrefs != null) {
//            return sSharedPrefs;
//        }
//
//        //Option 1:
//        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
//
//        //Option 2:
//        // Alternatively, you can create a new instance here
//        // with something like this:
//        // getInstance(MyCustomApplication.getAppContext());
//    }

    public void put(Key key, String val) {
        AppExecutor.getExecutorService().submit(() -> {
            doEdit();
            mEditor.putString(key.name(), val);
            doCommit();
            objectMap.put(key.name(), val);
        });
    }

    public void put(Key key, int val) {
        AppExecutor.getExecutorService().submit(() -> {
            doEdit();
            mEditor.putInt(key.name(), val);
            doCommit();
            objectMap.put(key.name(), val);
        });
    }

    public void put(Key key, boolean val) {
        AppExecutor.getExecutorService().submit(() -> {
            doEdit();
            mEditor.putBoolean(key.name(), val);
            doCommit();
            objectMap.put(key.name(), val);
        });
    }

    public void put(Key key, float val) {
        AppExecutor.getExecutorService().submit(() -> {
            doEdit();
            mEditor.putFloat(key.name(), val);
            doCommit();
            objectMap.put(key.name(), val);
        });
    }

    /**
     * Convenience method for storing doubles.
     * <p/>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to store.
     * @param val The new value for the preference.
     */
    public void put(Key key, double val) {
        AppExecutor.getExecutorService().submit(() -> {
            doEdit();
            mEditor.putString(key.name(), String.valueOf(val));
            doCommit();
            objectMap.put(key.name(), val);
        });
    }

    public void put(Key key, long val) {
        AppExecutor.getExecutorService().submit(() -> {
            doEdit();
            mEditor.putLong(key.name(), val);
            doCommit();
            objectMap.put(key.name(), val);
        });

    }

    public String getString(Key key, String defaultValue) {
        Object o = objectMap.get(key.name());

        if (o == null) {
            String value = mPref.getString(key.name(), defaultValue);
            if (value == null) {
                return null;
            }
            objectMap.put(key.name(), value);
        }

        return (String) o;
    }

    public String getString(Key key) {
        Object o = objectMap.get(key.name());

        if (o == null) {
            String value = mPref.getString(key.name(), null);
            if (value == null) {
                return null;
            }
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (String) o;
    }

    public int getInt(Key key) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            int value = mPref.getInt(key.name(), 0);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (int) o;

    }

    public int getInt(Key key, int defaultValue) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            int value = mPref.getInt(key.name(), defaultValue);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (int) o;

    }

    public long getLong(Key key) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            long value = mPref.getLong(key.name(), 0);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (long) o;

//        return mPref.getLong(key.name(), 0);
    }

    public long getLong(Key key, long defaultValue) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            long value = mPref.getLong(key.name(), defaultValue);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (long) o;
    }

    public float getFloat(Key key) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            float value = mPref.getFloat(key.name(), 0);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (float) o;


    }

    public float getFloat(Key key, float defaultValue) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            float value = mPref.getFloat(key.name(), defaultValue);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (float) o;
    }

    /**
     * Convenience method for retrieving doubles.
     * <p/>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to fetch.
     */
    public double getDouble(Key key) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            double value = getDouble(key, 0);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (double) o;
    }

    /**
     * Convenience method for retrieving doubles.
     * <p/>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to fetch.
     */
    public double getDouble(Key key, double defaultValue) {
        try {

            Object o = objectMap.get(key.name());

            if (o == null) {
                double value = Double.valueOf(mPref.getString(key.name(), String.valueOf(defaultValue)));
                objectMap.put(key.name(), value);
                o = objectMap.get(key.name());
            }

            return (double) o;

//            return Double.valueOf(mPref.getString(key.name(), String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(Key key, boolean defaultValue) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            boolean value = mPref.getBoolean(key.name(), defaultValue);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (boolean) o;
    }

    public boolean getBoolean(Key key) {

        Object o = objectMap.get(key.name());

        if (o == null) {
            boolean value = mPref.getBoolean(key.name(), false);
            objectMap.put(key.name(), value);
            o = objectMap.get(key.name());
        }

        return (boolean) o;

//        return mPref.getBoolean(key.name(), false);
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The enum of the key(s) to be removed.
     */
    public void remove(Key... keys) {
        AppExecutor.getExecutorService().submit(() -> {
            doEdit();
            for (Key key : keys) {
                mEditor.remove(key.name());
                objectMap.remove(key.name());
            }
            doCommit();
        });
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    public void clear() {
        AppExecutor.getExecutorService().submit(() -> {
            doEdit();
            mEditor.clear();
            doCommit();
            objectMap.clear();
        });
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.apply();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.apply();
            mEditor = null;
        }
    }
}