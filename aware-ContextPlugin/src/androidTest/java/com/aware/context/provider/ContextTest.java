package com.aware.context.provider;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Suppress;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.storage.ContextStorage;
import com.aware.context.storage.PersistenceContextStorage;
import com.aware.context.transform.ContextPropertySerialization;
import com.google.common.collect.ImmutableMap;

//TODO: change to work on mocks
@Suppress
public class ContextTest extends AndroidTestCase {
    private static final String TEST_CONTEXT_PREFERENCES = "TEST_CONTEXT_PREFERENCES";
    private static final String TEST_CONTEXT_PROPERTY_ID_1 = "TEST_CONTEXT_PROPERTY_1";
    private static final String TEST_CONTEXT_PROPERTY_ID_2 = "TEST_CONTEXT_PROPERTY_2";
    private static final GenericContextProperty TEST_CONTEXT_PROPERTY_1 = new GenericContextProperty(
            TEST_CONTEXT_PROPERTY_ID_1, ImmutableMap.<String, Object>of("k1", "v1", "k2", 2));
    private static final GenericContextProperty TEST_CONTEXT_PROPERTY_2 = new GenericContextProperty(
            TEST_CONTEXT_PROPERTY_ID_2, ImmutableMap.<String, Object>of("k3", "v3", "k4", 4));

    @Override
    public void setUp() {
        ContextStorage<String> contextStorage = new PersistenceContextStorage(getContext()
                .getSharedPreferences(TEST_CONTEXT_PREFERENCES, android.content.Context.MODE_PRIVATE));
        ContextPropertySerialization<GenericContextProperty> contextPropertySerialization =
                new ContextPropertySerialization<>(GenericContextProperty.class);
        contextStorage.setContextProperty(TEST_CONTEXT_PROPERTY_ID_1, contextPropertySerialization.CONTEXT_SERIALIZER
                .apply(TEST_CONTEXT_PROPERTY_1));
        contextStorage.setContextProperty(TEST_CONTEXT_PROPERTY_ID_2, contextPropertySerialization.CONTEXT_SERIALIZER
                .apply(TEST_CONTEXT_PROPERTY_2));
    }

    @Override
    public void tearDown() {
        getContext().getSharedPreferences(
                TEST_CONTEXT_PREFERENCES, android.content.Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }

    public void testGettingContextProperty() {
        //given
        Context context = new Context(getContext().getContentResolver(),
                new ContextPropertySerialization<>(GenericContextProperty.class));

        //when
        GenericContextProperty contextProperty = context.getContextProperty(TEST_CONTEXT_PROPERTY_ID_1);

        //then
        assertEquals(contextProperty, TEST_CONTEXT_PROPERTY_1);

    }
}