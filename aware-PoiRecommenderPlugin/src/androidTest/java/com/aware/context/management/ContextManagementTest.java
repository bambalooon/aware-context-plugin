package com.aware.context.management;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.Suppress;

//TODO: change to test PersistenceContextStorage (setup like in ContextTest), test multithreaded read and write
@Suppress
public class ContextManagementTest extends InstrumentationTestCase {
//    private ContextStorage<GenericContextProperty> contextStorage;
//
//    private ContextManagement contextManagement;
//
//    @Override
//    public void setUp() {
//        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
//        contextStorage = mock(PersistenceContextStorage.class);
//        contextManagement = new ContextManagement(contextStorage);
//    }
//
//    public void testConcurrentRead() {
//        //given
//        final Context<GenericContextProperty> expectedContext = new Context<>();
//        given(contextStorage.getContext()).willReturn(expectedContext);
//        Runnable work = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    //when
//                    Context<GenericContextProperty> context = contextManagement.getContext();
//
//                    //then
//                    assertEquals(context, expectedContext);
//                }
//            }
//        };
//
//        //when
//        List<Thread> threads = new LinkedList<>();
//        for (int i = 0; i < 100; i++) {
//            Thread thread = new Thread(work);
//            threads.add(thread);
//            thread.start();
//        }
//
//        //then
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                //do nothing
//            }
//        }
//    }
//
//    public void testConcurrentWrite() {
//        //given
//        final Context<GenericContextProperty> emptyContext = new Context<>();
//        given(contextStorage.getContext()).willReturn(emptyContext);
//        Runnable work = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    //given
//                    final int no = i;
//
//                    //when
//                    GenericContextProperty contextProperty =
//                            new GenericContextProperty(Thread.currentThread().getName() + no, new HashMap<String, Object>());
//                    contextManagement.setContextProperty(contextProperty);
//                    Context<GenericContextProperty> context = contextManagement.getContext();
//
//                    //then
//                    assertTrue(FluentIterable.from(context.getContextProperties())
//                            .anyMatch(new Predicate<GenericContextProperty>() {
//                                @Override
//                                public boolean apply(GenericContextProperty contextProperty) {
//                                    return contextProperty.getId().equals(Thread.currentThread().getName() + no);
//                                }
//                            }));
//                }
//            }
//        };
//
//        //when
//        List<Thread> threads = new LinkedList<>();
//        for (int i = 0; i < 100; i++) {
//            Thread thread = new Thread(work);
//            threads.add(thread);
//            thread.start();
//        }
//
//        //then
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                //do nothing
//            }
//        }
//    }
}