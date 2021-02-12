package com.animsh.moviem.model.tvshow;

/**
 * Created by animsh on 2/12/2021.
 */
public class TVShowNetwork {
    private String name;
    private int id;
    private String logo_path;
    private String origin_country;

    public TVShowNetwork() {
    }

    public TVShowNetwork(String name, int id, String logo_path, String origin_country) {
        this.name = name;
        this.id = id;
        this.logo_path = logo_path;
        this.origin_country = origin_country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }
}
