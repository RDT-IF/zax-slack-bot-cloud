package org.rdtif.zaxslackbot.interpreter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

class LanguagePatternLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    LanguagePattern load(String fileName) {
        try {
            return mapper.readValue(openPatternFileStream(fileName), LanguagePattern.class);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private InputStream openPatternFileStream(String fileName) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            throw new FileNotFoundException("Pattern file '" + fileName + "' not found");
        }
        return stream;
    }
}
