package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssetSubscribeServiceTest {

    @Mock
    private FieldManagerService fieldManagerService;

    @InjectMocks
    private AssetSubscribeService assetSubscribeService;

    private static final Long DUMMY_ASSET_ID = 1L;

    private static final String DUMMY_USER_LOGIN = "dummy";

    private static final List<String> DUMMY_ASSET_IDS_LIST = Collections.singletonList(DUMMY_ASSET_ID.toString());

    private static final List<String> DUMMY_EMPTY_LIST = Collections.emptyList();

    @Test
    public void testSubscribe() {
        try (MockedStatic<UserUtils> mocked = Mockito.mockStatic(UserUtils.class)) {
            mocked.when(UserUtils::getUserLogin).thenReturn(DUMMY_USER_LOGIN);

            when(fieldManagerService.getTabularValues(DUMMY_ASSET_ID, Fields.SUBSCRIBER)).thenReturn(DUMMY_EMPTY_LIST);
            when(fieldManagerService.saveMetadata(DUMMY_ASSET_ID, Fields.SUBSCRIBER, UserUtils.getUserLogin()))
                    .thenReturn(Boolean.TRUE);

            Boolean result = assetSubscribeService.subscribe(DUMMY_ASSET_ID);

            verify(fieldManagerService).getTabularValues(DUMMY_ASSET_ID, Fields.SUBSCRIBER);
            verify(fieldManagerService).saveMetadata(DUMMY_ASSET_ID, Fields.SUBSCRIBER, UserUtils.getUserLogin());
            verifyNoMoreInteractions(fieldManagerService);
            assertTrue(result);
        }
    }

    @Test
    public void testUnsubscribe() {
        try (MockedStatic<UserUtils> mocked = Mockito.mockStatic(UserUtils.class)) {
            mocked.when(UserUtils::getUserLogin).thenReturn(DUMMY_USER_LOGIN);

            when(fieldManagerService.deleteMetadataValue(DUMMY_ASSET_ID, Fields.SUBSCRIBER, UserUtils.getUserLogin()))
                    .thenReturn(Boolean.TRUE);

            Boolean result = assetSubscribeService.unsubscribe(DUMMY_ASSET_ID);

            verify(fieldManagerService).deleteMetadataValue(DUMMY_ASSET_ID, Fields.SUBSCRIBER, UserUtils.getUserLogin());
            verifyNoMoreInteractions(fieldManagerService);
            assertTrue(result);
        }
    }

    @Test
    public void testIsSubscribe() {
        when(fieldManagerService.getTabularValues(DUMMY_ASSET_ID, Fields.SUBSCRIBER)).thenReturn(DUMMY_ASSET_IDS_LIST);

        Boolean result = assetSubscribeService.isSubscribed(DUMMY_ASSET_ID);

        verify(fieldManagerService).getTabularValues(DUMMY_ASSET_ID, Fields.SUBSCRIBER);
        verifyNoMoreInteractions(fieldManagerService);
        assertTrue(result);
    }
}
