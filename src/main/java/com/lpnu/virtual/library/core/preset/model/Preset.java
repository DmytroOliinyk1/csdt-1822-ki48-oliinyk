package com.lpnu.virtual.library.core.preset.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Preset implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private List<PresetField> fields;
}