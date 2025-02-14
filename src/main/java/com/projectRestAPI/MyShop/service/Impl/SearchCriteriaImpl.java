package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.service.SearchCriteriaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchCriteriaImpl implements SearchCriteriaService {
    public List<SearchCriteria> parseCriteria(List<String> criteriaArray) {
        List<SearchCriteria> criteriaList = new ArrayList<>();

        if (criteriaArray != null && !criteriaArray.isEmpty()) {
            for (String c : criteriaArray) {
                int operationIndex = findOperationIndex(c);
                if (operationIndex > 0) {
                    String key = c.substring(0, operationIndex).trim(); // Lấy phần trước toán tử
                    String operation = c.substring(operationIndex, operationIndex + 1); // Toán tử
                    String value = c.substring(operationIndex + 1).trim(); // Lấy phần sau toán tử

                    criteriaList.add(new SearchCriteria(key, operation, value));
                } else {
                    throw new IllegalArgumentException("Invalid criteria format. Expected format: key=operation=value");
                }
            }
        }

        return criteriaList;
    }


    private int findOperationIndex(String criteria) {
        String[] operations = {":", ">", "<", "like"};
        for (String op : operations) {
            int index = criteria.indexOf(op);
            if (index > 0) {
                return index;
            }
        }
        return -1; // Không tìm thấy toán tử
    }
}
