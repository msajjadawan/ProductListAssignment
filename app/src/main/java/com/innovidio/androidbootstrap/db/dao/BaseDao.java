package com.innovidio.androidbootstrap.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     * @return The SQLite row id
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(T obj);

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     * @return The SQLite row ids
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(List<T> obj);

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    public abstract void update(T obj);

    /**
     * Update an array of objects from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    public abstract void update(List<T> obj);

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    public abstract void delete(T obj);

    @Transaction
    public void upsert(T obj) {
        long id = insert(obj);
        if (id == -1) {
            update(obj);
        }
    }

    @Transaction
    public void upsert(List<T> objList) {
        List<Long> insertResult = insert(objList);
        List<T> updateList = new ArrayList<>();

        for (int i = 0; i < insertResult.size(); i++) {
            if (insertResult.get(i) == -1) {
                updateList.add(objList.get(i));
            }
        }

        if (!updateList.isEmpty()) {
            update(updateList);
        }
    }

    public String getTableName() {
        Class clazz = (Class) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0];
        String tableName = clazz.getSimpleName();
        return tableName;
    }
    @RawQuery
    protected abstract List<T> doFindAll(SupportSQLiteQuery query);

    public List<T> findAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select * from " + getTableName());
        return doFindAll(query);
    }

}
