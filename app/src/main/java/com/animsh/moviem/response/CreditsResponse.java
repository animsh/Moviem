package com.animsh.moviem.response;

import com.animsh.moviem.model.Cast;
import com.animsh.moviem.model.Crew;

import java.util.List;

/**
 * Created by animsh on 2/15/2021.
 */
public class CreditsResponse {
    private int id;
    private List<Cast> cast;
    private List<Crew> crew;

    public CreditsResponse(int id, List<Cast> cast, List<Crew> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
