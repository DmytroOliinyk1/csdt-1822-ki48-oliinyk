package com.lpnu.virtual.library.util;

import com.lpnu.virtual.library.common.model.Pagination;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class PaginationUtils {
    public static Pagination initPagination(Pagination pagination) {
        return pagination != null ? pagination : new Pagination();
    }

    public static List<Long> getAssetIdsOnPage(List<Long> ids, Pagination pagination, Boolean increasePage) {
        List<Long> result;
        if (pagination.getSize() == 0) {
            result = Collections.emptyList();
        } else {
            int pageNumber =
                    pagination.getPage() > pagination.getPages() ? pagination.getPages() : (Math.max(pagination.getPage(),
                            1));
            int startOffset = (pageNumber - 1) * pagination.getPageSize();
            result = ids.subList(startOffset,
                    Math.min(startOffset + pagination.getPageSize(), pagination.getSize()));
        }

        if (increasePage) {
//            pagination.increasePage();
        }
        return result;
    }

    public static Pagination createPagination(Integer page, String searchId) {
        return page != null && page > 0 ? new Pagination(page, searchId) : null;
    }
}
