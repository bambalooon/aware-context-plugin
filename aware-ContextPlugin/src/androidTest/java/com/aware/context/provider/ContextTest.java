package com.aware.context.provider;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.transform.ContextPropertySerialization;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

//TODO: (mocks) add custom InstrumentationTestRunner that sets dexmaker path to cache (http://stackoverflow.com/questions/12267572/mockito-dexmaker-on-android)
public class ContextTest extends InstrumentationTestCase {
    private final String contextPropertyId = "ContextPropertyId";
    private final String contextPropertyJson = "ContextPropertyJson";
    private final GenericContextProperty expectedContextProperty = new GenericContextProperty(
            contextPropertyId, Maps.<String, Object>newHashMap());
    private final Uri contextPropertyUri = Uri.withAppendedPath(ContextContract.Properties.CONTENT_URI,
            contextPropertyId);
    private final MatrixCursor contextPropertyCursor = new MatrixCursor(ContextContract.Properties.PROJECTION_ALL, 1);
    {
        contextPropertyCursor.addRow(new String[] {contextPropertyId, contextPropertyJson});
    }
    private final Map<Uri, Cursor> expectedResults = ImmutableMap.<Uri, Cursor>builder()
            .put(contextPropertyUri, contextPropertyCursor)
            .build();

    //mocks
    private MockContentResolver mockContentResolver = new MockContentResolver();
    private MockContentProvider mockContentProvider = new MockContentProviderWithMappedUris(expectedResults);
    private ContextPropertySerialization<GenericContextProperty> contextPropertySerializationMock;

    private Context context;

    @Override
    public void setUp() {
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
        mockContentResolver.addProvider(ContextContract.AUTHORITY, mockContentProvider);
        contextPropertySerializationMock = mock(ContextPropertySerialization.class);
        context = new Context(mockContentResolver, contextPropertySerializationMock);
    }

    public void testSuccessfulGetOfContextProperty() {
        //given
        given(contextPropertySerializationMock.deserialize(contextPropertyJson)).willReturn(expectedContextProperty);

        //when
        GenericContextProperty contextProperty = context.getContextProperty(contextPropertyId);

        //then
        assertSame(contextProperty, expectedContextProperty);
    }

    private class MockContentProviderWithMappedUris extends MockContentProvider {
        private final Map<Uri, Cursor> expectedResults;

        private MockContentProviderWithMappedUris(Map<Uri, Cursor> expectedResults) {
            this.expectedResults = expectedResults;
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            return expectedResults.get(uri);
        }
    }
}