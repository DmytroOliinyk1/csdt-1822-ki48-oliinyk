package com.lpnu.virtual.library.core.feed.service;

import com.lpnu.virtual.library.common.service.CacheService;
import com.lpnu.virtual.library.core.asset.service.AssetMetadataService;
import com.lpnu.virtual.library.core.asset.service.AssetPreviewService;
import com.lpnu.virtual.library.core.feed.model.SavedSearch;
import com.lpnu.virtual.library.core.feed.repository.FeedRepository;
import com.lpnu.virtual.library.core.feed.repository.FeedRepositoryExtended;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.search.MetadataSearchManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedServiceTest {

    @Mock
    private FeedRepository feedRepo;

    @Mock
    private FeedRepositoryExtended feedRepoExt;

    @Mock
    private AssetMetadataService assetMetadataService;

    @Mock
    private CacheService cacheService;

    @Mock
    private MetadataSearchManager metadataSearchManager;

    @Mock
    private AssetPreviewService assetPreviewService;

    @InjectMocks
    private FeedService feedService;

    @Test
    public void testGetAssetIds() {
        try (MockedStatic<UserUtils> mocked = Mockito.mockStatic(UserUtils.class)) {
            mocked.when(UserUtils::getUserLogin).thenReturn("DUMMY_USER_LOGIN");

            when(feedRepoExt.getAssetIdsByUserLoginDesc("DUMMY_USER_LOGIN")).thenReturn(
                    Collections.singletonList(1L));

            List<Long> result = feedService.getAssetIds();

            verify(feedRepoExt).getAssetIdsByUserLoginDesc("DUMMY_USER_LOGIN");
            verifyNoMoreInteractions(feedRepoExt);

            assertArrayEquals(result.toArray(), Collections.singletonList(1L).toArray());
        }
    }

    @Test
    public void testLogSearchForOne() {
        try (MockedStatic<UserUtils> mocked = Mockito.mockStatic(UserUtils.class)) {
            mocked.when(UserUtils::getUserLogin).thenReturn("DUMMY_USER_LOGIN");

            SavedSearch savedSearch = new SavedSearch();
            savedSearch.setId(1L);

            when(feedRepo.save(any())).thenReturn(
                    savedSearch);

            SavedSearch result = feedService.logSearch(1L);

            verify(feedRepo).save(any());
            verifyNoMoreInteractions(feedRepo);

            assertEquals(result, savedSearch);
        }
    }

    @Test
    public void testLogSearchForMany() {
        try (MockedStatic<UserUtils> mocked = Mockito.mockStatic(UserUtils.class)) {
            mocked.when(UserUtils::getUserLogin).thenReturn("DUMMY_USER_LOGIN");

            SavedSearch savedSearch = new SavedSearch();
            savedSearch.setAssetId(1L);

            when(feedRepo.save(any())).thenReturn(
                    savedSearch);

            List<SavedSearch> result = feedService.logSearch(Collections.singletonList(1L));

            verify(feedRepo).save(any());
            verifyNoMoreInteractions(feedRepo);

            assertEquals(result.stream().findFirst().get().getAssetId(), savedSearch.getAssetId());
        }
    }

    @Test
    public void testCleanUpForAllUser() {

        List<Long> dummyListIds = Collections.singletonList(1L);

        when(feedRepo.getUniqueUser())
                .thenReturn(Collections.singletonList("DUMMY_USER_LOGIN"));

        when(feedRepo.getCountByUserLogin("DUMMY_USER_LOGIN"))
                .thenReturn(100L);

        when(feedRepoExt.getOldIdsByUserLogin("DUMMY_USER_LOGIN"))
                .thenReturn(dummyListIds);

        doNothing().when(feedRepo).deleteAllById(dummyListIds);

        feedService.cleanUpForAllUser();

        verify(feedRepo).getUniqueUser();
        verify(feedRepo).getCountByUserLogin("DUMMY_USER_LOGIN");
        verify(feedRepoExt).getOldIdsByUserLogin("DUMMY_USER_LOGIN");
        verify(feedRepo).deleteAllById(dummyListIds);
        verifyNoMoreInteractions(feedRepo);
        verifyNoMoreInteractions(feedRepoExt);

    }
}
