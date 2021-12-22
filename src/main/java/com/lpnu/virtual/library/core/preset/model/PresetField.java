package com.lpnu.virtual.library.core.preset.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PresetField implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private List<String> authority;
    private Boolean visible = Boolean.TRUE;
    private Boolean hyperLink = Boolean.FALSE;

}
