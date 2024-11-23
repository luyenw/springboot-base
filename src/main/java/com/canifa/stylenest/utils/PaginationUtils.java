package com.canifa.stylenest.utils;

import com.canifa.stylenest.common.PageRequest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PaginationUtils {

    public static <T> List<T> paginate(List<T> list, PageRequest pageRequest) {
        int page = Math.max(pageRequest.getPage(), 1);
        int pageSize = Math.max(pageRequest.getPageSize(), 1);
        int offset = (page - 1) * pageSize;

        if (list.size() <= offset) {
            return Collections.emptyList();
        }

        if (pageRequest.getSortBy() != null && !pageRequest.getSortBy().isEmpty()) {
            String sortBy = pageRequest.getSortBy();
            String order = pageRequest.getOrder();

            Comparator<T> comparator = createComparator(sortBy, "desc".equalsIgnoreCase(order));
            if (comparator != null) {
                list = new ArrayList<>(list);
                list.sort(comparator);
            }
        }

        int toIndex = Math.min(offset + pageSize, list.size());
        return list.subList(offset, toIndex);
    }

    private static <T> Comparator<T> createComparator(String sortBy, boolean descending) {
        return (o1, o2) -> {
            try {
                Field field = o1.getClass().getDeclaredField(sortBy);
                field.setAccessible(true);
                Comparable value1 = (Comparable) field.get(o1);
                Comparable value2 = (Comparable) field.get(o2);
                int comparison = value1.compareTo(value2);
                return descending ? -comparison : comparison;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid sortBy field: " + sortBy, e);
            }
        };
    }
}

