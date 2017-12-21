package com.example.georg.DAO;

import com.example.georg.DTO.PlantDTO;

import java.util.List;

/**
 * Created by Georg on 21.12.2017.
 */

public interface IPlantDAO {
    List<PlantDTO> fetchPlants(String searchTerm);
}
