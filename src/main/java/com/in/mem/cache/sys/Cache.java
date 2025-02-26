package com.in.mem.cache.sys;

import java.util.List;

public interface Cache {

    public List<Integer> getRestrictedSegments();

    public List<String> lookupIdMappings(final String id);

    public List<Integer> lookupSegments(final String id);
}
