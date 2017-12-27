package com.example.georg.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.georg.DTO.PlantDTO;

import org.json.JSONException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Georg on 24.12.2017.
 */

public class OfflinePlantDAO extends SQLiteOpenHelper implements IOfflinePlantDAO {

    public static final String PLANTS = "PLANTS";
    public static final String CACHE_ID = "CACHE_ID";
    public static final String GENUS = "GENUS";
    public static final String SPECIES = "SPECIES";
    public static final String GUID = "GUID";
    public static final String CULTIVAR = "CULTIVAR";
    public static final String COMMON = "COMMON";

    //there is no default-constructor in sqlite
    public OfflinePlantDAO(Context ctx){
        super(ctx,"plantplaces.db",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPlants = "CREATE TABLE " + PLANTS + " ( " + CACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GUID + " INTEGER, " + GENUS + " TEXT, " + SPECIES + " TEXT, " + CULTIVAR + " TEXT, " + COMMON + " TEXT " + " );";
        db.execSQL(createPlants);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) throws JSONException {
        return null;
    }

    @Override
    public void insert(PlantDTO plant){
        //create our content values, Das ContentValues-Objekt können wir dann in die SQLite Datenbank einfügen.
        ContentValues cv=new ContentValues();
        cv.put(GUID,plant.getGuid());
        cv.put(GENUS,plant.getGenus());
        cv.put(SPECIES,plant.getSpecies());
        cv.put(CULTIVAR,plant.getCultivar());
        cv.put(COMMON,plant.getCommon());
        //insert the record into the database, returns long primary key, lets chose genus column, just to get the cacheID
        long cacheID=getWritableDatabase().insert(PLANTS,GENUS,cv);
        //store our cache ID in our DTO
        plant.setCacheID(cacheID);
    }
    @Override
     public Set<Integer> fetchAllGuids(){
               // declare the return type.
                        Set<Integer> allGuids = new HashSet<Integer>();

                        // assemble SQL Statement.
                                String sql = "SELECT " + GUID + " FROM " + PLANTS;

                        // run the query.
                                Cursor cursor = getReadableDatabase().rawQuery(sql, null);

                       // did we get results?
                                if (cursor.getCount() > 0) {
                        // move to the first result.
                                cursor.moveToFirst();
                       //iterate over the results.
                                while (!cursor.isAfterLast()) {
                                // get the value.
                                        int guid = cursor.getInt(cursor.getColumnIndex(GUID));

                                        // add this GUID to our set of GUIDs.
                                                allGuids.add(Integer.valueOf(guid));

                                        // go to the next row.
                                                cursor.moveToNext();

                                    }

                            }

                        cursor.close();

                       return allGuids;

                    }
    @Override
    public int countPlants(){
        int plantCount=0;
        String sql="SELECT COUNT(*) FROM "+PLANTS;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        //did we get result?
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            plantCount=cursor.getInt(0);//columnidenx 0, because only 1 column

        }
        cursor.close();
        return plantCount;

    }
}
