package com.example.georg.DAO;

import com.example.georg.DTO.PlantDTO;

import java.util.Set;

/**
 * Created by Georg on 27.12.2017.
 */

public interface IOfflinePlantDAO extends IPlantDAO {
    void insert(PlantDTO plant);

    Set<Integer> fetchAllGuids();

    int countPlants();
}
