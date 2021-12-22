package com.lpnu.virtual.library.metadata.field.search;

import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.model.Values;
import com.lpnu.virtual.library.metadata.field.util.FieldUtils;
import com.lpnu.virtual.library.util.ValuesUtils;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class SearchUtils {
    public static SearchConstructor buildConstructorForMyPage() {
        SearchConstructor sc = new SearchConstructor();
        return sc.addGroup(Arrays.asList(
                        sc.isMeta(Fields.UPLOADER, UserUtils.getUserLogin()),
                        sc.isMeta(Fields.ASSET_TYPE, Values.BOOK_ASSET_TYPE)),
                Collections.singletonList(Operator.AND));
    }

    public static SearchConstructor buildConstructorForAllPage() {
        return new SearchConstructor()
                .is(Fields.ASSET_TYPE, Values.BOOK_ASSET_TYPE);
    }

    public static SearchConstructor buildConstructorWithFilters(List<FieldDto> fields) {
        String assetType = getAssetTypeForSearchCondition(fields);
        SearchConstructor sc = new SearchConstructor();

        if (!ValuesUtils.hasElements(fields)) {
            return sc.is(Fields.ASSET_TYPE, assetType);
        }

        List<MetadataCondition> mcs = new ArrayList<>();
        List<Operator> ops = new ArrayList<>();
        mcs.add(sc.isMeta(Fields.ASSET_TYPE, assetType));
        ops.add(Operator.AND);
        fields.forEach(f -> {
            if (StringUtils.isNotBlank(f.getDisplayValue())) {
                mcs.add(sc.likeMeta(f.getFieldId(), f.getDisplayValue()));
                ops.add(Operator.OR);
            }
        });
        return sc.addGroup(mcs, ops);
    }

    private static String getAssetTypeForSearchCondition(List<FieldDto> fields) {
        String assetType = FieldUtils.getFieldValue(fields, Fields.ASSET_TYPE);
        if (!StringUtils.isNotBlank(assetType)) {
            return Values.BOOK_ASSET_TYPE;
        }
        return assetType;
    }

    public static SearchConstructor buildConstructorForDefault() {
        return new SearchConstructor()
                .is(Fields.ASSET_TYPE, Values.BOOK_ASSET_TYPE);
    }

    public static SearchConstructor buildConstructorForSubscribed() {
        SearchConstructor sc = new SearchConstructor();
        return sc.addGroup(Arrays.asList(
                        sc.isMeta(Fields.SUBSCRIBER, UserUtils.getUserLogin()),
                        sc.isMeta(Fields.ASSET_TYPE, Values.BOOK_ASSET_TYPE)),
                Collections.singletonList(Operator.AND));

    }

    public static SearchConstructor buildConstructorForSearchAuthorByPseudonym(String pseudonym) {
        SearchConstructor sc = new SearchConstructor();
        return sc.addGroup(Arrays.asList(
                        sc.isMeta(Fields.PSEUDONYM, pseudonym),
                        sc.isMeta(Fields.ASSET_TYPE, Values.AUTHOR_ASSET_TYPE)),
                Collections.singletonList(Operator.AND));
    }

    public static SearchConstructor buildConstructorForAuthors(List<String> authors) {
        SearchConstructor sc = new SearchConstructor();

        if (!ValuesUtils.hasElements(authors)) {
            return sc.is(Fields.ASSET_TYPE, "not existing value");
        }

        List<MetadataCondition> mcs = new ArrayList<>();
        List<Operator> ops = new ArrayList<>();
        mcs.add(sc.isMeta(Fields.ASSET_TYPE, Values.AUTHOR_ASSET_TYPE));
        ops.add(Operator.AND);

        authors.forEach(a -> {
            mcs.add(sc.isMeta(Fields.PSEUDONYM, a));
            ops.add(Operator.OR);
        });
        return sc.addGroup(mcs, ops);
    }

    public static SearchConstructor buildConstructorForFeed(List<AssetMetadataDto> assets) {
        return new SearchConstructor()
                .isAnyOf(Fields.AUTHORS, FieldUtils.getFieldValues(assets, Fields.AUTHORS))
                .isAnyOf(Fields.GENRE, FieldUtils.getFieldValues(assets, Fields.GENRE))
                .isAnyOf(Fields.COUNTRY, FieldUtils.getFieldValues(assets, Fields.COUNTRY))
                .isAnyOf(Fields.LANGUAGE, FieldUtils.getFieldValues(assets, Fields.LANGUAGE));
    }

}
