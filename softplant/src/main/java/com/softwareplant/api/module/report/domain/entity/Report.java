package com.softwareplant.api.module.report.domain.entity;

import com.softwareplant.api.module.report.domain.dto.Film;
import com.softwareplant.api.module.report.domain.dto.People;
import com.softwareplant.api.module.report.domain.dto.Planet;
import com.softwareplant.api.module.report.util.UrlHelper;
import lombok.Data;

@Data
public class Report {
    private Long reportId;
    private Long filmId;
    private Long planetId;
    private Long characterId;
    private String filmName;
    private String characterName;
    private String planetName;

    public Report(People people, Planet planet, Film film) {
        this.characterId = UrlHelper.getIdFromUrl(people.getUrl(), "people");
        this.planetId = UrlHelper.getIdFromUrl(planet.getUrl(), "planets");
        this.filmId = UrlHelper.getIdFromUrl(film.getUrl(), "films");
        this.characterName = people.getName();
        this.planetName = planet.getName();
        this.filmName = film.getTitle();
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", filmId=" + filmId +
                ", planetId=" + planetId +
                ", characterId=" + characterId +
                ", filmName='" + filmName + '\'' +
                ", characterName='" + characterName + '\'' +
                ", planetName='" + planetName + '\'' +
                '}';
    }
}
