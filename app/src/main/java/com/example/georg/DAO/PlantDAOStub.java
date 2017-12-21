package com.example.georg.DAO;

import com.example.georg.DTO.PlantDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 21.12.2017.
 * Simulate network
 */

public class PlantDAOStub implements IPlantDAO {
    @Override
    public List<PlantDTO> fetchPlants(String searchTerm){

        //list is an interface, so not objecttype, class arraylist implements list interface
        List<PlantDTO>allPlants=new ArrayList<PlantDTO>();

        //pupulate the list of allplants with a hardcode, set of plants
        PlantDTO easternRedbud=new PlantDTO();
        easternRedbud.setGenus("Cercis");
        easternRedbud.setSpecies("canadensis");
        easternRedbud.setCultivar("");
        easternRedbud.setCommon("Eastern Rebud");

        allPlants.add(easternRedbud);

        PlantDTO chineseRedbud =new PlantDTO();
        chineseRedbud.setGenus("Cercis");
        chineseRedbud.setSpecies("Chinensis");
        chineseRedbud.setCultivar("");
        chineseRedbud.setCommon("Chinese Redbud");

        allPlants.add(chineseRedbud);

        PlantDTO lavendarTwistRedbud = new PlantDTO();
        lavendarTwistRedbud.setGenus("Cersis");
        lavendarTwistRedbud.setSpecies("canadensis");
        lavendarTwistRedbud.setCultivar("Lavender Twist");
        lavendarTwistRedbud.setCommon("Lavender Twist");

        allPlants.add(lavendarTwistRedbud);


        return allPlants;
    }
}
