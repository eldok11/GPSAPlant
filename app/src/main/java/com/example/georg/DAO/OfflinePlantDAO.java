package com.example.georg.DAO;

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

    //there is no default-constructor in sqlite
    public OfflinePlantDAO(Context ctx){
        super(ctx,"plantplaces.db",null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) throws JSONException {
        return null;
    }
}
