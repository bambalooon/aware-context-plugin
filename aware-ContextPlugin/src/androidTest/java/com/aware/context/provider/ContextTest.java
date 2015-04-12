package com.aware.context.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.test.ExceptionCatcher;
import com.aware.context.transform.ContextPropertySerialization;
import com.google.common.collect.ImmutableMap;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ContextTest extends TestCase {
    //context properties in content provider
    private final String contextPropertyId1 = "ContextPropertyId1";
    private final String contextPropertyId2 = "ContextPropertyId2";
    private final String contextPropertyId3 = "ContextPropertyId3";
    private final String noContextPropertyId = "noContextPropertyId";

    private final GenericContextProperty contextProperty1 = new GenericContextProperty(
            contextPropertyId1, Collections.<String, Object>emptyMap());
    private final GenericContextProperty contextProperty2 = new GenericContextProperty(
            contextPropertyId2, Collections.<String, Object>emptyMap());
    private final GenericContextProperty contextProperty3 = new GenericContextProperty(
            contextPropertyId3, Collections.<String, Object>emptyMap());

    //context properties for insertion to content provider
    private final String newContextPropertyId = "newContextPropertyId";
    private final GenericContextProperty newContextProperty = new GenericContextProperty(newContextPropertyId,
            ImmutableMap.<String, Object>of("newKey", "newVal", "numVal", 123));
    private final String newContextPropertyJson = "newContextPropertyJson";

    //context properties uri - for querying all properties and inserting new one
    private final Uri contextPropertiesUri = ContextContract.Properties.CONTENT_URI;

    //cursors used in Context - to check cursors closing
    private MatrixCursor contextProperty1Cursor;
    private MatrixCursor contextPropertiesCursor;
    private MatrixCursor noContextPropertyCursor;

    //mocks
    private ContextPropertySerialization<GenericContextProperty> contextPropertySerializationMock;

    private Context context;

    @Override
    public void setUp() {
        MockContentProvider mockContentProvider = generateMockContentProviderAndSetUpMocks();
        MockContentResolver mockContentResolver = new MockContentResolver();
        mockContentResolver.addProvider(ContextContract.AUTHORITY, mockContentProvider);
        context = new Context(mockContentResolver, contextPropertySerializationMock);
    }

    public void testSuccessfulGetOfContextProperty() {
        //when
        GenericContextProperty contextProperty = context.getContextProperty(contextPropertyId1);

        //then
        assertSame(contextProperty, contextProperty1);
        assertTrue(contextProperty1Cursor.isClosed());
        verify(contextPropertySerializationMock).deserialize(anyString());
    }

    public void testGettingNonExistingContextPropertyCursorNull() {
        //when
        GenericContextProperty contextProperty = context.getContextProperty("wrong context property ID");

        //then
        assertNull(contextProperty);
        verifyZeroInteractions(contextPropertySerializationMock);
    }

    public void testGettingNonExistingContextPropertyCursorNotNull() {
        //when
        GenericContextProperty contextProperty = context.getContextProperty(noContextPropertyId);

        //then
        assertNull(contextProperty);
        assertTrue(noContextPropertyCursor.isClosed());
        verifyZeroInteractions(contextPropertySerializationMock);
    }

    public void testGettingContextPropertyFromEmptyContentProvider() {
        //given
        Context context = generateContextWithEmptyContentProvider();

        //when
        GenericContextProperty contextProperty = context.getContextProperty(contextPropertyId1);

        //then
        assertNull(contextProperty);
        verifyZeroInteractions(contextPropertySerializationMock);
    }

    public void testGettingContextPropertyWithNullId() {
        //when
        Exception caughtException = new ExceptionCatcher() {
            @Override
            protected void invoke() throws Exception {
                context.getContextProperty(null);
            }
        }.catchException();

        //then
        assertTrue(caughtException instanceof NullPointerException);
        assertEquals(caughtException.getMessage(), "Given ContextProperty ID cannot be null.");
        verifyZeroInteractions(contextPropertySerializationMock);
    }

    public void testSuccessfulGetOfAllContextProperties() {
        //given
        Map<String, GenericContextProperty> expectedContextProperties = ImmutableMap
                .<String, GenericContextProperty>builder()
                .put(contextPropertyId1, contextProperty1)
                .put(contextPropertyId2, contextProperty2)
                .put(contextPropertyId3, contextProperty3)
                .build();

        //when
        Map<String, GenericContextProperty> contextProperties = context.getContextProperties();

        //then
        assertEquals(contextProperties, expectedContextProperties);
        assertTrue(contextPropertiesCursor.isClosed());
        verify(contextPropertySerializationMock, times(3)).deserialize(anyString());
    }

    public void testGetOfAllContextPropertiesFromEmptyContentProvider() {
        //given
        Context context = generateContextWithEmptyContentProvider();

        //when
        Map<String, GenericContextProperty> contextProperties = context.getContextProperties();

        //then
        assertNotNull(contextProperties);
        assertTrue(contextProperties.isEmpty());
        verifyZeroInteractions(contextPropertySerializationMock);
    }

    public void testSuccessfulSetOfContextProperty() {
        //when
        context.setContextProperty(newContextPropertyId, newContextProperty);

        //then
        verify(contextPropertySerializationMock).serialize(newContextProperty);
    }

    public void testSettingNewContextPropertyWithNullContextPropertyId() {
        //when
        Exception caughtException = new ExceptionCatcher() {
            @Override
            protected void invoke() throws Exception {
                context.setContextProperty(null, contextProperty1);
            }
        }.catchException();

        //then
        assertTrue(caughtException instanceof NullPointerException);
        assertEquals(caughtException.getMessage(), "Given ContextProperty ID cannot be null.");
        verifyZeroInteractions(contextPropertySerializationMock);
    }

    public void testSettingNewContextPropertyWithNullContextProperty() {
        //when
        Exception caughtException = new ExceptionCatcher() {
            @Override
            protected void invoke() throws Exception {
                context.setContextProperty(contextPropertyId1, null);
            }
        }.catchException();

        //then
        assertTrue(caughtException instanceof NullPointerException);
        assertEquals(caughtException.getMessage(), "Given ContextProperty cannot be null.");
        verifyZeroInteractions(contextPropertySerializationMock);
    }

    public void testSettingNewContextPropertyWithNotEqualIdsOfContextPropertyIdAndContextProperty() {
        //when
        Exception caughtException = new ExceptionCatcher() {
            @Override
            protected void invoke() throws Exception {
                context.setContextProperty("different context ID", contextProperty1);
            }
        }.catchException();

        //then
        assertTrue(caughtException instanceof IllegalArgumentException);
        assertEquals(caughtException.getMessage(), "Given ContextProperty ID must be equal to ContextProperty's ID.");
        verifyZeroInteractions(contextPropertySerializationMock);
    }

    private MockContentProvider generateMockContentProviderAndSetUpMocks() {
        //serialized context properties
        String contextPropertyJson1 = "ContextPropertyJson1";
        String contextPropertyJson2 = "ContextPropertyJson2";
        String contextPropertyJson3 = "ContextPropertyJson3";

        //setup serialization mock
        contextPropertySerializationMock = mock(ContextPropertySerialization.class);
        given(contextPropertySerializationMock.deserialize(contextPropertyJson1)).willReturn(contextProperty1);
        given(contextPropertySerializationMock.deserialize(contextPropertyJson2)).willReturn(contextProperty2);
        given(contextPropertySerializationMock.deserialize(contextPropertyJson3)).willReturn(contextProperty3);

        given(contextPropertySerializationMock.serialize(newContextProperty)).willReturn(newContextPropertyJson);

        //context properties uris
        Uri contextPropertyUri1 = Uri.withAppendedPath(ContextContract.Properties.CONTENT_URI, contextPropertyId1);
        Uri contextPropertyUri2 = Uri.withAppendedPath(ContextContract.Properties.CONTENT_URI, contextPropertyId2);
        Uri contextPropertyUri3 = Uri.withAppendedPath(ContextContract.Properties.CONTENT_URI, contextPropertyId3);
        Uri noContextPropertyUri = Uri.withAppendedPath(ContextContract.Properties.CONTENT_URI, noContextPropertyId);

        //cursors holding context properties
        contextProperty1Cursor = new MatrixCursor(ContextContract.Properties.PROJECTION_ALL, 1);
        contextProperty1Cursor.addRow(new String[] {contextPropertyId1, contextPropertyJson1});
        MatrixCursor contextProperty2Cursor = new MatrixCursor(ContextContract.Properties.PROJECTION_ALL, 1);
        contextProperty2Cursor.addRow(new String[] {contextPropertyId2, contextPropertyJson2});
        MatrixCursor contextProperty3Cursor = new MatrixCursor(ContextContract.Properties.PROJECTION_ALL, 1);
        contextProperty3Cursor.addRow(new String[] {contextPropertyId3, contextPropertyJson3});

        noContextPropertyCursor = new MatrixCursor(ContextContract.Properties.PROJECTION_ALL, 0);

        contextPropertiesCursor = new MatrixCursor(ContextContract.Properties.PROJECTION_ALL, 3);
        contextPropertiesCursor.addRow(new String[] {contextPropertyId1, contextPropertyJson1});
        contextPropertiesCursor.addRow(new String[] {contextPropertyId2, contextPropertyJson2});
        contextPropertiesCursor.addRow(new String[] {contextPropertyId3, contextPropertyJson3});

        //expected results of mocked content provider
        Map<Uri, Cursor> expectedResults = ImmutableMap.<Uri, Cursor>builder()
                .put(contextPropertyUri1, contextProperty1Cursor)
                .put(contextPropertyUri2, contextProperty2Cursor)
                .put(contextPropertyUri3, contextProperty3Cursor)
                .put(noContextPropertyUri, noContextPropertyCursor)
                .put(contextPropertiesUri, contextPropertiesCursor)
                .build();

        return new MockContentProviderWithMappedUris(expectedResults);
    }

    private Context generateContextWithEmptyContentProvider() {
        MockContentProvider emptyMockContentProvider =
                new MockContentProviderWithMappedUris(Collections.<Uri, Cursor>emptyMap());
        MockContentResolver mockContentResolver = new MockContentResolver();
        mockContentResolver.addProvider(ContextContract.AUTHORITY, emptyMockContentProvider);
        return new Context(mockContentResolver, contextPropertySerializationMock);
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

        @Override
        public Uri insert(Uri uri, ContentValues values) {
            if (uri.equals(contextPropertiesUri)) {
                //check if required values exist
                assertTrue(values.containsKey(ContextContract.Properties._ID));
                assertTrue(values.containsKey(ContextContract.Properties._CONTEXT_PROPERTY));

                String contextPropertyId = values.getAsString(ContextContract.Properties._ID);
                String contextPropertyJson = values.getAsString(ContextContract.Properties._CONTEXT_PROPERTY);

                //check if required values have expected value
                assertEquals(contextPropertyId, newContextPropertyId);
                assertEquals(contextPropertyJson, newContextPropertyJson);

                return Uri.EMPTY;
            }
            return super.insert(uri, values);
        }
    }
}