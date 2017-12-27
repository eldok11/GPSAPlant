package com.example.georg.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.georg.DTO.PlantDTO;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Georg on 24.12.2017.
 */

public class OfflinePlantDAO extends SQLiteOpenHelper implements IPlantDAO{

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

        String createPlants= "CREATE TABLE "+ PLANTS +" ("+ CACHE_ID +"INTEGER PRIMARY KEY AUTOINCREMENT,"+
                GUID+" INTEGER, "+GENUS+" TEXT, "+ SPECIES+" TEXT, "+CULTIVAR+" TEXT, "+ COMMON+" TEXT "+");";
                db.execSQL(createPlants);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) throws JSONException {
        return null;
    }

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

}
