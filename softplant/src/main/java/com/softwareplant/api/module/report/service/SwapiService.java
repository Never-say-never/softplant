package com.softwareplant.api.module.report.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareplant.api.module.report.domain.dto.*;
import com.softwareplant.api.module.report.domain.entity.Report;
import com.softwareplant.api.module.report.exception.ReportNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SwapiService implements ISwapiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers;
    private String swapiHost;

    public SwapiService(String swapiHost) {
        headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("User-Agent", "insomnia/6.0.2");
        this.swapiHost = swapiHost;
    }

    /**
     *
     * @param path
     * @param params
     * @return
     */
    private String executeGetRequest(final String path, final HashMap<String, String> params) {
        final String requestUri = this.buildRequestUri(path, params);

        return this.executeGetRequest(requestUri);
    }

    /**
     *
     * @param requestUri
     * @return
     */
    private String executeGetRequest(final String requestUri) {
        log.info("reQ {} in {}", requestUri, Thread.currentThread().getName());

        HttpEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET,
                new HttpEntity<>(headers), String.class);
        log.info("reP in {}", Thread.currentThread().getName());

        return response.getBody();
    }

    /**
     *
     * @param path
     * @param params
     * @return
     */
    protected String buildRequestUri(final String path, final HashMap<String, String> params) {
        if (path == null || path.isEmpty()){
            throw new IllegalArgumentException("Bad path: " + path);
        }

        final UriComponentsBuilder builder =  UriComponentsBuilder
                .fromHttpUrl(swapiHost.concat(path));
        params.forEach(builder::queryParam);

        return builder.toUriString();
    }

    /**
     *
     * @param elements
     * @param <T>
     * @return
     */
    private <T> Optional<T> getLastElement(List<T> elements) {
        if (elements != null && !elements.isEmpty()) {
            int last = elements.size() - 1;
            return Optional.of(elements.get(last));
        }

        return Optional.empty();
    }

    /**
     *
     * @param json
     * @return
     */
    protected Optional<People> getPeopleFromResponse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            JsonNode rootNode = mapper.readTree(json);
            log.info("elements in response {}", rootNode.get("count").toString());
            if (rootNode.get("count").asInt() != 0) {
                List<People> list = mapper.convertValue(
                        rootNode.get("results"),
                        new TypeReference<List<People>>() {});

                return this.getLastElement(list);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    protected <T> T getObjectFromResponse(String json, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(json);
            return mapper.convertValue(rootNode, type);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     *
     * @param criteria
     * @return
     */
    private Optional<People> getCharacter(final Criteria criteria) {
        log.info("start get People");
        final String json = this.executeGetRequest(
                "people/",
                new HashMap<String, String>() {{
                    put("search", criteria.getCharacterPhrase());
                }}
            );

        return this.getPeopleFromResponse(json);
    }

    /**
     *
     * @param character
     * @return
     */
    private Planet getPlanet(final People character) {
        final String jsonPlanet = this.executeGetRequest(character.getHomeworld());

        return this.getObjectFromResponse(jsonPlanet, Planet.class);
    }

    /**
     *
     * @param criteria
     * @param planet
     * @return
     */
    private boolean isCharacterFromRequestedPlanet(Criteria criteria, Planet planet) {
        return planet.getName().equalsIgnoreCase(criteria.getPlanetName());
    }

    /**
     *
     * @param character
     * @return
     */
    private List<Film> getFilms(final People character) {
        if (character.getFilms().isEmpty()) {
            return Collections.emptyList();
        }

        return character.getFilms()
                .parallelStream()
                .map(e -> this.getObjectFromResponse(this.executeGetRequest(e), Film.class))
                .collect(Collectors.toList());
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public List<Report> getReport(final Criteria criteria) throws ReportNotFoundException {
        log.info("start get Report");

        final List<Report> reports = new ArrayList<>();
        final People people = this.getCharacter(criteria)
                .orElseThrow(() -> new ReportNotFoundException("o`ups no character was found"));

        final Planet planet = this.getPlanet(people);

        if (this.isCharacterFromRequestedPlanet(criteria, planet)) {
            final List<Film> films = this.getFilms(people);

            return films.stream()
                    .map(film -> new Report(people, planet, film))
                    .collect(Collectors.toList());
        }
        log.info("reports " + reports);
        return reports;
    }
}
