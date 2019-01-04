package com.softwareplant.api.module.report.service;

import com.softwareplant.api.container.Application;
import com.softwareplant.api.module.report.domain.dto.People;
import com.softwareplant.api.module.report.domain.dto.Planet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class SwapiServiceTest extends SwapiService {

    public SwapiServiceTest() {
        super("https://swapi.co/api/");
    }

    @Test
    public void testJsonPars() {
        People people = this.getPeopleFromResponse("{\"count\":1,\"next\":null,\"previous\":null,\"results\":" +
                "[{\"name\":\"Luke Skywalker\",\"height\":\"172\",\"mass\":\"77\",\"hair_color\":\"blond\",\"skin_color\"" +
                ":\"fair\",\"eye_color\":\"blue\",\"birth_year\":\"19BBY\",\"gender\":\"male\",\"homeworld\":" +
                "\"https://swapi.co/api/planets/1/\",\"films\":[\"https://swapi.co/api/films/2/\"," +
                "\"https://swapi.co/api/films/6/\",\"https://swapi.co/api/films/3/\",\"https://swapi.co/api/films/1/\"," +
                "\"https://swapi.co/api/films/7/\"],\"species\":[\"https://swapi.co/api/species/1/\"],\"vehicles\":" +
                "[\"https://swapi.co/api/vehicles/14/\",\"https://swapi.co/api/vehicles/30/\"],\"starships\":" +
                "[\"https://swapi.co/api/starships/12/\",\"https://swapi.co/api/starships/22/\"],\"created\":" +
                "\"2014-12-09T13:50:51.644000Z\",\"edited\":\"2014-12-20T21:17:56.891000Z\",\"url\":" +
                "\"https://swapi.co/api/people/1/\"}]}").get();
        assertTrue(people.getName().equals("Luke Skywalker"));
    }

    @Test
    public void testJsonParsGetPlanetFromResponse() {
        String uri = this.buildRequestUri("demo/", new HashMap<String, String>(){{ put("param1", "100"); }});
        assertTrue(uri.equals("https://swapi.co/api/demo/?param1=100"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJsonParsGetPlanetFromResponseNull() {
        this.buildRequestUri(null, new HashMap<String, String>(){{ put("param1", "100"); }});
    }

    @Test
    public void testJsonParsGetPlanetFromResponseBadParams() {
        String uri = this.buildRequestUri("demo/",
                new HashMap<String, String>(){{
                    put("param1", null);
                }});

        System.out.println(uri);
        assertTrue(uri.equals("https://swapi.co/api/demo/?param1"));
    }

    @Test
    public void testJsonParsGetPlanetFromResponseDemo() {
        Planet planet = this.getObjectFromResponse(
                "{\"name\":\"Naboo\",\"rotation_period\":\"26\",\"orbital_period\":\"312\",\"diameter\":\"12120\"," +
                        "\"climate\":\"temperate\",\"gravity\":\"1 standard\",\"terrain\":\"grassy hills, swamps, fores" +
                        "ts, mountains\",\"surface_water\":\"12\",\"population\":\"4500000000\",\"residents\":[\"https:" +
                        "//swapi.co/api/people/3/\",\"https://swapi.co/api/people/21/\",\"https://swapi.co/api/people/" +
                        "36/\",\"https://swapi.co/api/people/37/\",\"https://swapi.co/api/people/38/\",\"https://swapi." +
                        "co/api/people/39/\",\"https://swapi.co/api/people/42/\",\"https://swapi.co/api/people/60/\",\"" +
                        "https://swapi.co/api/people/61/\",\"https://swapi.co/api/people/66/\",\"https://swapi.co/api/pe" +
                        "ople/35/\"],\"films\":[\"https://swapi.co/api/films/5/\",\"https://swapi.co/api/films/4/\",\"htt" +
                        "ps://swapi.co/api/films/6/\",\"https://swapi.co/api/films/3/\"],\"created\":\"2014-12-10T11:52:" +
                        "31.066000Z\",\"edited\":\"2014-12-20T20:58:18.430000Z\",\"url\":\"https://swapi.co/api/planets/" +
                        "8/\"}",
                Planet.class);
        assertTrue(planet.getName().equals("Naboo"));
    }
}