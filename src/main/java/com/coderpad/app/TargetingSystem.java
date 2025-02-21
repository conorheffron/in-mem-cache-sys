package com.coderpad.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TargetingSystem {

    private final Cache userDataCache;

    public TargetingSystem(Cache cacheImplementation) {
        this.userDataCache = cacheImplementation;
    }

    public Collection<Integer> lookupUserSegments(final String id, boolean allowRestrictedSegments,
                                                  final int segmentLimit) {
        // Get all values from all linked ids
        ArrayList<Integer> idSegments = getAllValuesFromLinkedIds(id);

        // De-duplicate the returned values
        ArrayList<Integer> deDupedList = deDupSegments(idSegments);

        // Remove all restricted values
        deDupedList = removeRestrictedSegments(allowRestrictedSegments, deDupedList);

        // Apply limit to values returned
        return applyLimit(segmentLimit, deDupedList);
    }

    private List<Integer> applyLimit(int segmentLimit, ArrayList<Integer> deDupedList) {
        List<Integer> limitedSegments = new ArrayList<>();
        if (segmentLimit > 0) {
            for (int i = 0; i < segmentLimit; i++) {
                limitedSegments.add(deDupedList.get(i));
            }
        }
        return limitedSegments;
    }

    private ArrayList<Integer> removeRestrictedSegments(boolean allowRestrictedSegments, ArrayList<Integer> deDupedList) {
        List<Integer> restrictedSegments = userDataCache.getRestrictedSegments();
        if (!allowRestrictedSegments) {
            for (Integer restrictedSegment : restrictedSegments) {
                deDupedList.remove(restrictedSegment);
            }
        }
        return deDupedList;
    }

    private ArrayList<Integer> deDupSegments(ArrayList<Integer> idSegments) {
        ArrayList<Integer> deDupedList = new ArrayList<>();
        for (Integer idSegment : idSegments) {
            if (!deDupedList.contains(idSegment)) {
                deDupedList.add(idSegment);
            }
        }
        return deDupedList;
    }

    private ArrayList<Integer> getAllValuesFromLinkedIds(String id) {
        ArrayList<Integer> idSegments = new ArrayList<>();
        List<String> idMappings = userDataCache.lookupIdMappings(id);
        for (String idMapping : idMappings) {
            List<Integer> x = userDataCache.lookupSegments(idMapping);
            if (x != null) {
                idSegments.addAll(x);
            }
        }
        return idSegments;
    }
}
