package com.coderpad.app;

// ====================================================================================================================
// DO NOT EDIT THIS CLASS
// ====================================================================================================================

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CacheImplementation implements Cache {

    private static final String ID_MAP_FILE_NAME = "idmappings.txt";
    private static final String SEGMENT_DATA_FILE_NAME = "segmentdata.txt";
    private static final String RESTRICTED_SEGMENTS_FILE_NAME = "restrictedsegments.txt";
    private static final String DELIMITER = ":";

    private final ClassLoader classLoader = CacheImplementation.class.getClassLoader();
    private final Map<String, List<String>> idMappings = new HashMap<>();
    private final Map<String, List<Integer>> segmentData = new HashMap<>();
    private final List<Integer> restrictedSegments = new ArrayList<>();

    public CacheImplementation() {
        setupCache();
    }

    public List<Integer> getRestrictedSegments() {
        return restrictedSegments;
    }

    public List<String> lookupIdMappings(final String id) {
        return idMappings.get(id);
    }

    public List<Integer> lookupSegments(final String id) {
        return segmentData.get(id);
    }

    private void setupCache() {
        loadIdMappingDataIntoMemory();
        loadSegmentDataIntoMemory();
        loadRestrictedSegmentsIntoMemory();
    }

    private void loadIdMappingDataIntoMemory() {
        try {
            streamFile(ID_MAP_FILE_NAME).filter(line -> line.contains(DELIMITER))
                    .forEach(line -> {
                        final String id = line.split(DELIMITER)[0];
                        final String idMappingListString = line.split(DELIMITER)[1];
                        final List<String> mappingList = Optional.ofNullable(idMappings.get(id))
                                .orElse(new ArrayList<>());
                        mappingList.addAll(Arrays.asList(idMappingListString.split(",")));
                        idMappings.put(id, mappingList);
                    });
            System.out.println("ID Mapping Cache Setup Complete");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSegmentDataIntoMemory() {
        try {
            streamFile(SEGMENT_DATA_FILE_NAME).filter(line -> line.contains(DELIMITER))
                    .forEach(line -> {
                        final String id = line.split(DELIMITER)[0];
                        final String segmentIdString = line.split(DELIMITER)[1];
                        final List<Integer> segmentIdsToAdd = Stream.of(segmentIdString.split(","))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());
                        final List<Integer> segmentDataList = Optional.ofNullable(segmentData.get(id))
                                .orElse(new ArrayList<>());
                        segmentDataList.addAll(segmentIdsToAdd);
                        segmentData.put(id, segmentDataList);
                    });
            System.out.println("Segment Data Cache Setup Complete");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRestrictedSegmentsIntoMemory() {
        try {
            streamFile(RESTRICTED_SEGMENTS_FILE_NAME).forEach(line -> restrictedSegments.add(Integer.parseInt(line)));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        System.out.println("Restricted Segment Cache Setup Complete");
    }

    private Stream<String> streamFile(final String filename) throws IOException {
        return Files.lines(getAbsoluteFilePath(filename));
    }

    private Path getAbsoluteFilePath(final String filename) {
        return Paths.get(getFile(filename).getAbsolutePath());
    }

    private File getFile(final String filename) {
        return new File(classLoader.getResource(filename)
                .getFile());
    }
}
