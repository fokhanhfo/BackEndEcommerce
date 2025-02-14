package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;

import java.util.List;

public interface SearchCriteriaService {
    public List<SearchCriteria> parseCriteria(List<String> criteriaArray);
}
