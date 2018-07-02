package com.pubfin.tools.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class DBUtils {

    private DBHelper dbHelper;
    private String db_Name = "sh_db", table_Name = "person";
    private SQLiteDatabase sqLiteDatabase_read, sqLiteDatabase_write;//得到一个可读,可写的SQLiteDatabase对象
    public DBUtils (Context context)
    {
        dbHelper = new DBHelper(context, db_Name, null, 1);
        sqLiteDatabase_read = dbHelper.getReadableDatabase();
        sqLiteDatabase_write = dbHelper.getWritableDatabase();
    }

    /**
     * 插入数据
     * @param content
     * @param time
     */
    public void insert(String content, String time)
    {
        System.out.println(time + "----------------------------0");
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        contentValues.put("time", time);
        sqLiteDatabase_write.insert(table_Name, null, contentValues);
        System.out.println("插入成功");
    }

    /**
     * 查询数据
     * @param time
     * @return
     */
    public List<HashMap<String, Object>> search(String time)
    {
        System.out.println(time + "--------------------------------1");
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        //表名，想要显示的列，where子句，where子句对应的值，分组方式，having条件，排序方式
        Cursor cursor = sqLiteDatabase_read.query(table_Name, new String[]{"_id", "content"}, "time=?", new String[]{time}, null, null, "_id desc");
        while (cursor.moveToNext())
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("content", cursor.getString(cursor.getColumnIndex("content")));
            map.put("id", cursor.getString(cursor.getColumnIndex("_id")));
            list.add(map);
            return list;
        }
        return null;
    }

    /**
     * 查询数据
     * @return
     */
    public List<HashMap<String, Object>> searchAll()
    {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        //表名，想要显示的列，where子句，where子句对应的值，分组方式，having条件，排序方式
        Cursor cursor = sqLiteDatabase_read.query(table_Name, new String[]{"*"}, null, null, null, null, null);
        while (cursor.moveToNext())
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("content", cursor.getString(cursor.getColumnIndex("content")));
            map.put("id", cursor.getString(cursor.getColumnIndex("_id")));
            list.add(map);
            return list;
        }
        return null;
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(String[] ids)
    {
        String whereClauses = "_id=?";
        sqLiteDatabase_write.delete(table_Name, whereClauses, ids);
    }

}
