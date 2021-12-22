package com.lpnu.virtual.library.metadata.field.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Fields {
    String UPLOADER = "FIELD.UPLOADER";
    String SUBSCRIBER = "FIELD.SUBSCRIPTION";
    String ASSET_TYPE = "FIELD.ASSET.TYPE";
    String NAME = "FIELD.NAME";
    String AUTHORS = "FIELD.AUTHORS";
    String PSEUDONYM = "FIELD.PSEUDONYM";
    String GENRE = "FIELD.GENRE";
    String COUNTRY = "FIELD.COUNTRY";
    String LANGUAGE = "FIELD.LANGUAGE";

    List<String> FEED_GROUP = Collections.unmodifiableList(Arrays.asList(AUTHORS, GENRE, COUNTRY, LANGUAGE));
}