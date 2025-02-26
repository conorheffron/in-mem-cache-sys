package com.in.mem.cache.sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TargetingSystemTest {

    // object under test
    private TargetingSystem targetingSystem;

    // mocks
    private Cache cacheMock;

    // test values, variables, constants
    private static final String TEST_ID_1 = "ID19876";
    private static final String TEST_ID_2 = "ID29876";
    private static final String TEST_ID_3 = "ID39876";
    private static final String TEST_DEVICE_ID_1 = "device12345";
    private static final String TEST_DEVICE_ID_2 = "device32345";
    private static final String TEST_DEVICE_ID_3 = "device43456";
    private final Map<String, List<String>> idMappings = new HashMap<>();
    private final Map<String, List<Integer>> segmentData = new HashMap<>();
    private List<Integer> restrictedSegments = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        // initialize mocks
        cacheMock = mock(Cache.class);

        // initialize targeting system instance under test
        targetingSystem = new TargetingSystem(cacheMock);

        // reset / populate test data
        populateRestrictedSegments();
        populateIdMappings();
        populateSegmentData();
    }

    @Test
    public void whenLookingUpUserSegmentsForId_thenSegmentsAreReturned() {
        // given
        List<Integer> expectedResult = Arrays.asList(11111, 11112, 11113, 11114, 11115,
                11110, 88880, 11116, 11117, 11118);
        int expectedSize = expectedResult.size();

        // when
        Collection<Integer> result = new TargetingSystem(new CacheImplementation())
                .lookupUserSegments(TEST_ID_1, true, 10);

        // then
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(expectedSize));
        assertThat(result, is(equalTo(expectedResult)));
    }

    @Test
    public void test_whenLookingUpUserSegmentsForTestId_1_withCacheMock_thenSegmentsAreReturned() {
        // given
        List<Integer> expectedResult = Arrays.asList(11111, 11112, 11113, 11114, 11115, 11110,
                88880, 11116, 11117, 11118);
        int expectedSize = expectedResult.size();

        when(cacheMock.lookupIdMappings(TEST_ID_1)).thenReturn(idMappings.get(TEST_ID_1));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_1).get(0))).thenReturn(segmentData.get(idMappings.get(TEST_ID_1).get(0)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_1).get(1))).thenReturn(segmentData.get(idMappings.get(TEST_ID_1).get(1)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_1).get(2))).thenReturn(segmentData.get(idMappings.get(TEST_ID_1).get(2)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_1).get(3))).thenReturn(segmentData.get(idMappings.get(TEST_ID_1).get(3)));
        when(cacheMock.getRestrictedSegments()).thenReturn(restrictedSegments);

        // when
        Collection<Integer> result = targetingSystem
                .lookupUserSegments(TEST_ID_1, true, 10);

        // then
        verify(cacheMock).lookupIdMappings(TEST_ID_1);
        verify(cacheMock, times(4)).lookupSegments(anyString());
        verify(cacheMock).getRestrictedSegments();

        assertThat(result.size(), is(expectedSize));
        assertThat(result, is(equalTo(expectedResult)));
    }

    @Test
    public void test_whenLookingUpUserSegmentsForTestId_2_withCacheMock_thenSegmentsAreReturned() {
        // given
        List<Integer> expectedResult = Arrays.asList(22226, 22227, 22228, 22229, 22220, 44440,
                33331, 33332, 33333, 33334);
        int expectedSize = expectedResult.size();

        when(cacheMock.lookupIdMappings(TEST_ID_2)).thenReturn(idMappings.get(TEST_ID_2));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_2).get(0)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_2).get(0)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_2).get(1)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_2).get(1)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_2).get(2)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_2).get(2)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_2).get(3)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_2).get(3)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_2).get(4)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_2).get(4)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_2).get(5)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_2).get(5)));
        when(cacheMock.getRestrictedSegments()).thenReturn(restrictedSegments);

        // when
        Collection<Integer> result = targetingSystem
                .lookupUserSegments(TEST_ID_2, true, 10);

        // then
        verify(cacheMock).lookupIdMappings(TEST_ID_2);
        verify(cacheMock, times(6)).lookupSegments(anyString());
        verify(cacheMock).getRestrictedSegments();

        assertThat(result.size(), is(expectedSize));
        assertThat(result, is(equalTo(expectedResult)));
    }

    @Test
    public void test_whenLookingUpUserSegmentsForTestId_3_withCacheMock_thenSegmentsAreReturned() {
        // given
        List<Integer> expectedResult = Arrays.asList(55556, 55557, 55558, 55559, 55550, 88880,
                66661, 66662, 66663, 66664, 66665, 66669, 66666, 66667, 66668, 66660);
        int expectedSize = 16;

        when(cacheMock.lookupIdMappings(TEST_ID_3)).thenReturn(idMappings.get(TEST_ID_3));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_3).get(0)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_3).get(0)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_3).get(1)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_3).get(1)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_3).get(2)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_3).get(2)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_3).get(3)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_3).get(3)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_3).get(4)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_3).get(4)));
        when(cacheMock.lookupSegments(idMappings.get(TEST_ID_3).get(5)))
                .thenReturn(segmentData.get(idMappings.get(TEST_ID_3).get(5)));
        when(cacheMock.getRestrictedSegments()).thenReturn(restrictedSegments);

        // when
        Collection<Integer> result = targetingSystem
                .lookupUserSegments(TEST_ID_3, true, 16);

        // then
        verify(cacheMock).lookupIdMappings(TEST_ID_3);
        verify(cacheMock, times(6)).lookupSegments(anyString());
        verify(cacheMock).getRestrictedSegments();

        assertThat(result.size(), is(expectedSize));
        assertThat(result, is(equalTo(expectedResult)));
    }

    @Test
    public void lookupUserSegments_allowRestrictedSegments_segmentLimit_is_three_thenSegmentsAreReturned() {
        // given
        List<Integer> expectedResult = Arrays.asList(11111, 11112, 11113);
        int expectedSize = expectedResult.size();

        when(cacheMock.lookupIdMappings(TEST_ID_1)).thenReturn(idMappings.get(TEST_ID_1));
        when(cacheMock.getRestrictedSegments()).thenReturn(restrictedSegments);
        when(cacheMock.lookupSegments(TEST_DEVICE_ID_1)).thenReturn(segmentData.get(TEST_DEVICE_ID_1));

        // when
        Collection<Integer> result = targetingSystem.lookupUserSegments(TEST_ID_1,
                true, 3);

        // then
        verify(cacheMock).lookupIdMappings(TEST_ID_1);
        verify(cacheMock).getRestrictedSegments();
        verify(cacheMock).lookupSegments(TEST_DEVICE_ID_1);

        assertThat(result.size(), is(expectedSize));
        assertThat(result, is(equalTo(expectedResult)));
    }

    @Test
    public void lookupUserSegments_byTestId_1_disallowRestrictedSegments_segmentLimit_is_three_thenSegmentsAreReturned() {
        // given
        List<Integer> expectedResult = Arrays.asList(11112, 11113, 11114);
        int expectedSize = expectedResult.size();

        when(cacheMock.lookupIdMappings(TEST_ID_1)).thenReturn(idMappings.get(TEST_ID_1));
        when(cacheMock.getRestrictedSegments()).thenReturn(restrictedSegments);
        when(cacheMock.lookupSegments(TEST_DEVICE_ID_1)).thenReturn(segmentData.get(TEST_DEVICE_ID_1));

        // when
        Collection<Integer> result = targetingSystem.lookupUserSegments(TEST_ID_1,
                false, 3);

        // then
        verify(cacheMock).lookupIdMappings(TEST_ID_1);
        verify(cacheMock).getRestrictedSegments();
        verify(cacheMock).lookupSegments(TEST_DEVICE_ID_1);

        assertThat(result.size(), is(expectedSize));
        assertThat(result, is(equalTo(expectedResult)));
    }

    @Test
    public void test_lookupUserSegments_byTestId_2_disallowRestrictedSegments_segmentLimit_is_three_thenSegmentsAreReturned() {
        // given
        List<Integer> expectedResult = Arrays.asList(55551, 55552, 55553);
        int expectedSize = expectedResult.size();

        when(cacheMock.lookupIdMappings(TEST_ID_2)).thenReturn(idMappings.get(TEST_ID_2));
        when(cacheMock.getRestrictedSegments()).thenReturn(restrictedSegments);
        when(cacheMock.lookupSegments(TEST_DEVICE_ID_2)).thenReturn(segmentData.get(TEST_DEVICE_ID_2));


        // when
        Collection<Integer> result = targetingSystem.lookupUserSegments(TEST_ID_2,
                false, 3);

        // then
        verify(cacheMock).lookupIdMappings(TEST_ID_2);
        verify(cacheMock).getRestrictedSegments();
        verify(cacheMock).lookupSegments(TEST_DEVICE_ID_2);

        assertThat(result.size(), is(expectedSize));
        assertThat(result, is(equalTo(expectedResult)));
    }

    @Test
    public void test_lookupUserSegments_disallowRestrictedSegments_segmentLimit_is_zero_thenSegmentsAreReturned() {
        // given
        when(cacheMock.lookupIdMappings(TEST_ID_3)).thenReturn(idMappings.get(TEST_ID_3));
        when(cacheMock.getRestrictedSegments()).thenReturn(restrictedSegments);
        when(cacheMock.lookupSegments(TEST_DEVICE_ID_3)).thenReturn(segmentData.get(TEST_DEVICE_ID_3));


        // when
        Collection<Integer> result = targetingSystem.lookupUserSegments(TEST_ID_3,
                false, 0);

        // then
        verify(cacheMock).lookupIdMappings(TEST_ID_3);
        verify(cacheMock).getRestrictedSegments();
        verify(cacheMock).lookupSegments(TEST_DEVICE_ID_3);

        assertThat(result, is(Collections.emptyList()));
    }

    private void populateRestrictedSegments() {
        restrictedSegments.clear();
        restrictedSegments = Arrays.asList(11111, 22222, 33333, 44444, 55555, 66666, 77777, 88888);
    }

    private void populateSegmentData() {
        segmentData.clear();
        segmentData.put("device12345", Arrays.asList(11111, 11112, 11113, 11114, 11115, 11110, 88880));
        segmentData.put("device13456", Arrays.asList(11116, 11117, 11118, 11119, 11110, 88885));
        segmentData.put("device14567", Arrays.asList(22221, 22222, 22223, 22224, 22225, 88880));
        segmentData.put("device21234", Arrays.asList(22226, 22227, 22228, 22229, 22220, 44440));
        segmentData.put("device22345", Arrays.asList(33331, 33332, 33333, 33334, 33335, 88880));
        segmentData.put("device23456", Arrays.asList(33336, 33337, 33338, 33339, 33330, 66660));
        segmentData.put("device24567", Arrays.asList(44441, 44442, 44443, 44444, 44445, 77775, 88880));
        segmentData.put("device31234", Arrays.asList(44446, 44447, 44448, 44449, 44440, 55556));
        segmentData.put("device32345", Arrays.asList(55551, 55552, 55553, 55554, 55555, 22228));
        segmentData.put("device33456", Arrays.asList(55556, 55557, 55558, 55559, 55550, 88880));
        segmentData.put("device34567", Arrays.asList(66661, 66662, 66663, 66664, 66665, 66669, 88880));
        segmentData.put("device41234", Arrays.asList(66666, 66667, 66668, 66669, 66660, 55559));
        segmentData.put("device42345", Arrays.asList(77771, 77772, 77773, 77774, 77775, 66664));
        segmentData.put("device43456", Arrays.asList(88881, 88882, 88883, 88884, 88885, 44448, 88880));
        segmentData.put("device44567", Arrays.asList(88886, 88887, 88888, 88889, 88880, 77772));
    }

    private void populateIdMappings() {
        idMappings.clear();
        List<String> deviceIds_a = Arrays.asList("device12345","device13456", "device14567",
                "device15678");
        List<String> deviceIds_b = Arrays.asList("device21234", "device22345", "device23456",
                "device24567", "device31234", "device32345");
        List<String> deviceIds_c = Arrays.asList("device33456", "device34567", "device41234",
                "device42345", "device43456", "device44567");
        idMappings.put(TEST_ID_1, deviceIds_a);
        idMappings.put(TEST_ID_2, deviceIds_b);
        idMappings.put(TEST_ID_3, deviceIds_c);
    }
}
