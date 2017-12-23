package com.example.georg.DAO;

import com.example.georg.DTO.PlantDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 21.12.2017.
 */

public class PlantDAO implements IPlantDAO{

    private final NetworkDAO networkDAO;

    public PlantDAO(){
        networkDAO=new NetworkDAO();

    }
    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) {
        String uri ="http://plantplaces.com/perl/mobile/viewplantsjson.pl?Combinded_Name="+searchTerm;
        //string result von unserem request
        String request = networkDAO.request(uri);
        //variable to holld our return data.
        List<PlantDTO> allPlants=new ArrayList<PlantDTO>();
        //return all Plants
        return allPlants;
    }
}
