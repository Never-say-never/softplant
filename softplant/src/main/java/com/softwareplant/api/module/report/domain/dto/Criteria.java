package com.softwareplant.api.module.report.domain.dto;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import javax.validation.constraints.NotEmpty;

@Data
public class Criteria {

    @NotEmpty(message = "Character Phrase is required")
    private String characterPhrase;

    @NotEmpty(message = "Planet Name is required")
    private String planetName;

    public String getHash() {
        return DigestUtils
            .md5Hex(characterPhrase.concat(planetName))
            .toUpperCase();
    }
}
