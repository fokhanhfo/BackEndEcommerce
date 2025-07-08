package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.ShippingAddressRequest;
import com.projectRestAPI.MyShop.dto.response.ShippingAddressResponse;
import com.projectRestAPI.MyShop.model.ShippingAddress;

import java.util.List;

public interface ShippingAddressService {
    ShippingAddressResponse create(ShippingAddressRequest request);
    List<ShippingAddressResponse> getAll();

    ShippingAddressResponse getById(Long id);

    ShippingAddressResponse update(Long id, ShippingAddressRequest request);

    void delete(Long id);
}
