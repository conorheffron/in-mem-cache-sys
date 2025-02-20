package com.coderpad.app;

// ====================================================================================================================
// DO NOT EDIT THIS CLASS
// ====================================================================================================================

import java.util.List;

public interface Cache {

    public List<Integer> getRestrictedSegments();

    public List<String> lookupIdMappings(final String id);

    public List<Integer> lookupSegments(final String id);
}
