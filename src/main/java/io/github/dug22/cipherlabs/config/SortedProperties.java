package io.github.dug22.cipherlabs.config;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation from Apache Commons. Credit goes to them
 * https://github.com/apache/commons-collections/blob/master/src/main/java/org/apache/commons/collections4/properties/SortedProperties.java
 *
 * Credit goes to Archimedes Trajano for supplying the idea on how to strip out automated comments.
 * https://stackoverflow.com/a/39043903/31344837
 */
public class SortedProperties extends Properties {

    public SortedProperties() {

    }

    // empty
    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return keyStream().map(k -> new AbstractMap.SimpleEntry<>(k, get(k))).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void store(final OutputStream out, final String comments) {
        try {
            super.store(new StripFirstLineStream(out), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized TreeSet<String> enumerateStringProperties(final TreeSet<String> result) {
        if (defaults != null) {
            result.addAll(defaults.stringPropertyNames());
        }
        for (final Enumeration<?> e = keys(); e.hasMoreElements(); ) {
            final Object k = e.nextElement();
            final Object v = get(k);
            if (k instanceof String && v instanceof String) {
                result.add((String) k);
            }
        }
        return result;
    }

    @Override
    public synchronized void forEach(final BiConsumer<? super Object, ? super Object> action) {
        keyStream().forEach(k -> action.accept(k, get(k)));
    }

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(keySet());
    }

    @Override
    public Set<Object> keySet() {
        return new TreeSet<>(super.keySet());
    }

    private Stream<Object> keyStream() {
        return keySet().stream();
    }

    @Override
    public Enumeration<?> propertyNames() {
        return Collections.enumeration(stringPropertyNames());
    }

    @Override
    public Set<String> stringPropertyNames() {
        return enumerateStringProperties(new TreeSet<>());
    }

    public Object getAppropriateProperty(String key) {
        Object value = super.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing configuration value: " + key);
        }

        return value;
    }

    private static class StripFirstLineStream extends FilterOutputStream {
        private boolean firstlineseen = false;

        public StripFirstLineStream(final OutputStream out) {
            super(out);
        }

        @Override
        public void write(final int b) throws IOException {
            if (firstlineseen) {
                super.write(b);
            } else if (b == '\n') {
                firstlineseen = true;
            }
        }
    }
}