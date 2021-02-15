package com.animsh.moviem.model;

/**
 * Created by animsh on 2/15/2021.
 */
public class Crew {
    private boolean adult;
    private int gender;
    private int id;
    private String known_for_department;
    private String name;
    private String original_name;
    private Number popularity;
    private String profile_path;
    private String credit_id;
    private String department;
    private String job;

    public Crew() {
    }

    public Crew(boolean adult, int gender, int id, String known_for_department, String name, String original_name, Number popularity, String profile_path, String credit_id, String department, String job) {
        this.adult = adult;
        this.gender = gender;
        this.id = id;
        this.known_for_department = known_for_department;
        this.name = name;
        this.original_name = original_name;
        this.popularity = popularity;
        this.profile_path = profile_path;
        this.credit_id = credit_id;
        this.department = department;
        this.job = job;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public Number getPopularity() {
        return popularity;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {
        return "https://image.tmdb.org/t/p/w500" + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
