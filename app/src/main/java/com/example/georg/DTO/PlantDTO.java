package com.example.georg.DTO;

/**
 * Created by Georg on 21.12.2017.
 * Diese Klasse besitzt die attribute für einen pflanze
 */

public class PlantDTO {
    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public int guid;//global identifier

    public long getCacheID() {
        return cacheID;
    }

    public void setCacheID(long cacheID) {
        this.cacheID = cacheID;
    }

    private long cacheID;
    private String genus;
    private String species;
    private String cultivar;
    private String common;

    public String toString(){
        return genus+" "+species+" "+cultivar+" "+common;
    }
}
