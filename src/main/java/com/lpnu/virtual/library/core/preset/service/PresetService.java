package com.lpnu.virtual.library.core.preset.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpnu.virtual.library.core.preset.model.Preset;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PresetService {
    private static final String PRESETS_JSON = "presets.json";
    private ObjectMapper configMapper;

    @PostConstruct
    private void init() {
        configMapper = new ObjectMapper();
    }

    public List<Preset> getAllPresets() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(PRESETS_JSON)) {
            JavaType type = configMapper.getTypeFactory().constructCollectionType(List.class, Preset.class);
            return configMapper.readValue(in, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON presets", e);
        }
    }

    public Preset getPreset(PresetCode code) {
        return getAllPresets().stream()
                .filter(p -> code.toString().equals(p.getCode()))
                .findFirst()
                .orElse(null);
    }
}
