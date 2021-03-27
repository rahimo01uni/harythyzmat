package com.eccomerce.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 21.06.2018.
 */

public class Db extends SQLiteOpenHelper {
    public static String db_name = "reklam.db";
    public static int db_ver = 7;
    Context x;


    private static final String KEY_ID = "id";
    private static final String TABLE_CART = "cart";
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_PRICE = "item_price";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_QTY = "item_qty";

    public Db(Context context) {

        super(context, db_name, null, db_ver);
        this.x=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table dil(dili varhar(3))");
        sqLiteDatabase.execSQL("create table yeri(yer varhar(3))");
        sqLiteDatabase.execSQL("create table liked(id integer)");
        sqLiteDatabase.execSQL("create table liked_usluga(id integer)");
        sqLiteDatabase.execSQL("create table rate(id varchar(10))");
        sqLiteDatabase.execSQL("create table rate_usluga(id varchar(10))");

        String TABLECARTS = "CREATE TABLE IF NOT EXISTS "+ TABLE_CART + "(" + KEY_ID + " INTEGER PRIMARY KEY, "+  ITEM_ID  +" INTEGER ," + ITEM_PRICE + " INTEGER ," + ITEM_NAME + " TEXT ,"+ ITEM_QTY + " INTEGER  )";
        sqLiteDatabase.execSQL(TABLECARTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + "  dil");
        sqLiteDatabase.execSQL("Drop table if exists " + "  yeri");
        sqLiteDatabase.execSQL("Drop table if exists " + "  liked");
        sqLiteDatabase.execSQL("Drop table if exists " + "  liked_usluga");
        sqLiteDatabase.execSQL("Drop table if exists " + "  rate");
        sqLiteDatabase.execSQL("Drop table if exists " + "  rate_usluga");
        String TABLECARTS = "Drop TABLE IF EXISTS "+ TABLE_CART ;
        sqLiteDatabase.execSQL(TABLECARTS);
        onCreate(sqLiteDatabase);
    }

    public String insert_item_cart(int id,int price, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String msg = "";
        String selectQuery =  "SELECT  * FROM " + TABLE_CART + " WHERE " + ITEM_ID + " = " + id ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount()>0){
            msg= "Iteam already added in Cart";

            /*
            cursor.moveToFirst();

            int qty=cursor.getInt(4);
            int cart_id=cursor.getInt(0);
            Log.e("cartid",cart_id+"");
            Log.e("cartid1",cart_id+"");
            ContentValues cv = new ContentValues();
            cv.put(ITEM_QTY,qty+1);//I am  updating flag here

            Log.e("cartid2",cart_id+"");
            db.update(TABLE_CART, cv, ""+KEY_ID+" = '"+ cart_id +"'" , null);
            */
            db.close();
        }else
        {

            ContentValues values=new ContentValues();
            values.put(ITEM_ID,id);
            values.put(ITEM_PRICE,price);
            values.put(ITEM_NAME,name);
            values.put(ITEM_QTY,1);
            long cart_id= db.insert(TABLE_CART,null,values);
            Log.e("cart_id",cart_id+"");
            msg="Iteam Added in Cart";
        }
        return msg;
    }

    public int display_qty_item(int id){
        Log.e("id",id+"");
        int qty=0;
        String selectQuery =  "SELECT  * FROM " + TABLE_CART + " WHERE " + ITEM_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                qty=cursor.getInt(4);
                return qty;
            } while (cursor.moveToNext());
        }

        return qty;
    }

    public void update_plus(int id,int price,String name){

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT  * FROM " + TABLE_CART + " WHERE " + ITEM_ID + " = " + id;
        Log.e("selectrquery",selectQuery);
        int qty=0;
        int cart_id = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                qty=cursor.getInt(4);
                cart_id=cursor.getInt(0);
                Log.e("cartid",cart_id+"");
            } while (cursor.moveToNext());
        }

        Log.e("cartid1",cart_id+"");
        ContentValues cv = new ContentValues();
        cv.put(ITEM_QTY,qty+1);//I am  updating flag here

        Log.e("cartid2",cart_id+"");
        db.update(TABLE_CART, cv, ""+KEY_ID+" = '"+ cart_id +"'" , null);
        db.close();
    }

    public  void update_minus(int id,int price,String name){

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT  * FROM " + TABLE_CART + " WHERE " + ITEM_ID + " = " + id ;
        Log.e("selectrquery",selectQuery);
        int qty=0;
        int cart_id = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                qty=cursor.getInt(4);
                cart_id=cursor.getInt(0);
                Log.e("cartid",cart_id+"");
            } while (cursor.moveToNext());
        }

        Log.e("cartid1",cart_id+"");
        ContentValues cv = new ContentValues();
        cv.put(ITEM_QTY,qty-1);//I am  updating flag here

        Log.e("cartid2",cart_id+"");
        db.update(TABLE_CART, cv, ""+KEY_ID+" = '"+ cart_id +"'" , null);
        db.close();
    }
    public boolean check(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT  * FROM " + TABLE_CART + " WHERE " + ITEM_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int qty=cursor.getInt(4);
                if (qty>=1){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public void delet_data_pages(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql_trunct="DELETE FROM "+TABLE_CART;
        db.execSQL(sql_trunct);
    }

    public void deletrow(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql_trunct="DELETE FROM "+ TABLE_CART+ " WHERE " + ITEM_ID + " = " + id ;
        db.execSQL(sql_trunct);
    }

    public Cursor  getCartData() {
        String selectQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public int get_sum_price_qty(){
        int serving =0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM("+ITEM_QTY+") FROM "+TABLE_CART, null);
        if(cursor.moveToFirst()) {
            serving = cursor.getInt(0);
        }

        return serving;
    }



    //Dil
    public String get_dil() {
        try {


            SQLiteDatabase db = this.getWritableDatabase();
            String dil = "";
            Cursor c = db.query("dil", null, null, null, null, null, null, null);
            if (c.moveToNext()) dil = c.getString(c.getColumnIndex("dili"));
            c.close();
            Log.d("dili2",dil);
            return dil;
        } catch (NullPointerException ss) {
        }

        return "";
    }
    public String get_rate() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String id = "";
            Cursor c = db.query("rate", null, null, null, null, null, null, null);
            if (c.moveToNext()) id = c.getString(c.getColumnIndex("id"));
            c.close();
            return id;
        } catch (NullPointerException ss) {
        }

        return "";
    }
    public  boolean isin(String table, String id){
        boolean b=false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery("Select id from "+table+" where id=?",new String[]{id});
        if(c.moveToNext())b=true;
        return  b;
    }
    public int get_liked() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int id = 0;
            Cursor c = db.query("liked", null, null, null, null, null, null, null);
            if (c.moveToNext()) id = c.getInt(c.getColumnIndex("id"));
            c.close();
            return id;
        } catch (NullPointerException ss) {
        }

        return 0;
    }
    public int get_usluga_liked() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int id = 0;
            Cursor c = db.query("liked_usluga", null, null, null, null, null, null, null);
            if (c.moveToNext()) id = c.getInt(c.getColumnIndex("id"));
            c.close();
            return id;
        } catch (NullPointerException ss) {
        }

        return 0;
    }
    public String get_yeri() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String yer = "";
            Cursor c = db.query("yeri", null, null, null, null, null, null, null);
            if (c.moveToNext()) yer = c.getString(c.getColumnIndex("yer"));
            c.close();
            return yer;
        } catch (NullPointerException ss) {
        }

        return "";
    }

    public void inser_dil(String dil) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("dil", null, null);
        ContentValues c = new ContentValues();
        c.put("dili", dil);
        Log.d("dili1",dil);
        db.insert("dil", null, c);
    }
    public void inser_liked(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("id", id);
        db.insert("liked", null, c);
    }
    public void inser_rate(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("id", id);
        db.insert("rate", null, c);
    }
    public void inser_rate_usluga(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("id", id);
        db.insert("rate_usluga", null, c);
    }
    public void delete_liked(String id){
        SQLiteDatabase db= getWritableDatabase();
        db.delete("liked","id" + "=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void delete_liked_usluga(String id){
        SQLiteDatabase db= getWritableDatabase();
        db.delete("liked_usluga","id" + "=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void inser_liked_usluga(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("liked_usluga", null, null);
        ContentValues c = new ContentValues();
        c.put("id", id);
        db.insert("liked_usluga", null, c);
    }
    public void inser_yeri(String yer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("yeri", null, null);
        ContentValues c = new ContentValues();
        c.put("yer", yer);
        db.insert("yeri", null, c);
    }

}
