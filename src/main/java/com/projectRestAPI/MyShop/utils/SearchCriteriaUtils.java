package com.projectRestAPI.MyShop.utils;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;

import java.util.List;

public class SearchCriteriaUtils {
    public static void addCriteria(List<SearchCriteria> criteriaList, String key, String operation, Object value) {
        if (value != null) {
            criteriaList.add(new SearchCriteria(key, operation, value));
        }
    }
}
