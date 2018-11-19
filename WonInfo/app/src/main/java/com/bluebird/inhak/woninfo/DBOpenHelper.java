package com.bluebird.inhak.woninfo;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;
import static java.security.AccessController.getContext;

/**
 * Created by InHak on 2018-01-27.
 */
public class DBOpenHelper {
    private static final String DATABASE_NAME = "woninfo.db";
    private static final int DATABASE_VERSION = 37; //db 바꿀때마다 버전업(중요)
    public static SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private Context context;


    private class DBHelper extends SQLiteOpenHelper {

        //생성자
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBStruct.CreateDB._CREATE);

            //////////////// 테스트용 데이터 추가 코드
            //  String sql = "insert into "+DBStruct.CreateDB._TABLENAME + " " + "(primary_key, title, content) values( 'A01','웹정보서비스 이용방법','내용내용내용내용내용내용')";
            //  db.execSQL(sql);

            // Excel파일에서 정보 가져오면서 DB생성
            try {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open("menuExcelData.xls");
                Workbook workbook = Workbook.getWorkbook(inputStream);

                AssetManager assetManager1 = context.getAssets();
                InputStream inputStream1 = assetManager1.open("busExcelData.xls");
                Workbook workbook1 = Workbook.getWorkbook(inputStream1);
                //시트 이름
                Sheet sh = workbook.getSheet("Data");
                Sheet sh2 = workbook1.getSheet("Data2");

                String primaryKey="", title="",likes="", content="";
                int row = sh.getRows();
                int column = sh.getColumns();
                int row2 = sh2.getRows();
                int column2 = sh2.getColumns();

                for(int r=1; r<row; r++) {
                    for(int c=0; c<column; c++) {
                        Cell sheetCell = sh.getCell(c,r);
                        switch(c)
                        {
                            case 0:
                                primaryKey = sheetCell.getContents();
                                break;
                            case 1:
                                title = sheetCell.getContents();
                                break;
                            case 2:
                                likes = sheetCell.getContents();
                               break;
                            case 3:
                                content = sheetCell.getContents();
                                break;
                        }
                    }
                    String sql = "insert into "+DBStruct.CreateDB._TABLENAME + " " + "(primary_key, title,likes, content) values( '" +
                                    primaryKey + "','" + title + "','" +likes+ "','" + content + "')";
                    db.execSQL(sql);
                }
            }catch (Exception e){}

            /*
            Snackbar snackbar = Snackbar.make(context, "데이터베이스 생성",Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
            snackbar.show();
            */
        }

        //버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBStruct.CreateDB._TABLENAME);
            onCreate(db);
        }

        @Override
        public SQLiteDatabase getWritableDatabase() {
            return super.getWritableDatabase();
        }
    }

    public DBOpenHelper(Context context) {
        this.context = context;
    }

    public DBOpenHelper open() throws SQLException {
        dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }
}
