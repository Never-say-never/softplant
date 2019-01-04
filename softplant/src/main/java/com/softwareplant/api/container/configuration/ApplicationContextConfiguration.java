package com.softwareplant.api.container.configuration;

import com.softwareplant.api.module.report.domain.dto.Criteria;
import com.softwareplant.api.module.report.domain.dto.Film;
import com.softwareplant.api.module.report.domain.dto.People;
import com.softwareplant.api.module.report.domain.dto.Planet;
import com.softwareplant.api.module.report.domain.entity.Report;
import com.softwareplant.api.module.report.exception.ReportNotFoundException;
import com.softwareplant.api.module.report.service.ISwapiService;
import com.softwareplant.api.module.report.service.SwapiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@ComponentScan({
        "com.softwareplant.api.module.**",
        "com.softwareplant.api.container.filter.**"})
@Configuration
@EnableScheduling
@Import(value = {
    PersistenceConfiguration.class,
    SecurityConfiguration.class,
    SwaggerConfiguration.class})
public class ApplicationContextConfiguration {

    @Value("${external.host}")
    private String swapiHost;

    @Bean
    @Profile("development")
    public ISwapiService getSwapiService() {
        return new SwapiService(this.swapiHost);
    }

    @Bean
    @Profile("test")
    public ISwapiService getSwapiServiceTest() {
        return new SwapiService(this.swapiHost) {
            @Override
            public List<Report> getReport(Criteria criteria) throws ReportNotFoundException {
                People p = new People();
                p.setName("Blabla");
                p.setUrl("https://swapi.co/api/people/1/");
                Planet pl = new Planet();
                pl.setName("Propopo");
                pl.setUrl("https://swapi.co/api/planet/1/");
                Film f = new Film();
                f.setTitle("Trololl");
                f.setUrl("https://swapi.co/api/film/1/");
                Report r = new Report(p, pl, f);

                return new ArrayList<Report>(){{ add(r); }};
            }
        };
    }

    /**
     * Open CORS requests
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8081")
                        .allowedMethods("DELETE", "GET", "PUT")
                        .allowedHeaders("X-Auth-Token", "Content-Type", "Authorization", "Cache-Control")
                        .allowCredentials(true)
                        .maxAge(4800);
            }
        };
    }
}
