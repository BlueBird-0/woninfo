package com.bluebird.inhak.woninfo;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by InHak on 2018-01-27.
 */
public class DBOpenHelper {
    private static final String DATABASE_NAME = "woninfo.db";
    private static final int DATABASE_VERSION = 30;
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
                //시트 이름
                Sheet sh = workbook.getSheet("Data");

                String primaryKey="", title="", content="";
                int row = sh.getRows();
                int column = sh.getColumns();

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
                            //case 2:
                            //    content = sheetCell.getContents();
                            //   break;
                            case 3:
                                content = sheetCell.getContents();
                                break;
                        }
                    }
                    String sql = "insert into "+DBStruct.CreateDB._TABLENAME + " " + "(primary_key, title, content) values( '" +
                                    primaryKey + "','" + title + "','" + content + "')";
                    db.execSQL(sql);
                }
            }catch (Exception e){}

            Toast.makeText(context, "데이터베이스 생성", Toast.LENGTH_SHORT).show();
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
