package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Component
public class SolarSystemPrinter {
    private static final Logger log = LoggerFactory.getLogger(SolarSystemPrinter.class);

    public void setFile(String fileName) {
        Path filePath = Paths.get(fileName);
        List<String> strings = new LinkedList<>();
        try {
            strings = Files.readAllLines(filePath);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        strings.forEach(line -> log.info("{}", line));
    }
}
