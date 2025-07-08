package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.request.ShippingAddressRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.dto.response.ShippingAddressResponse;
import com.projectRestAPI.MyShop.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping-address")
@RequiredArgsConstructor
public class ShippingAddressController {
    @Autowired
    private ShippingAddressService shippingAddressService;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody ShippingAddressRequest request) {
        ShippingAddressResponse created = shippingAddressService.create(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .status("OK")
                .message("Created shipping address successfully")
                .errCode(0)
                .data(created)
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        List<ShippingAddressResponse> addresses = shippingAddressService.getAll();
        return ResponseEntity.ok(ResponseObject.builder()
                .status("OK")
                .message("Fetched shipping addresses successfully")
                .errCode(0)
                .data(addresses)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        ShippingAddressResponse address = shippingAddressService.getById(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .status("OK")
                .message("Fetched shipping address successfully")
                .errCode(0)
                .data(address)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id,
                                                 @RequestBody ShippingAddressRequest request) {
        ShippingAddressResponse updated = shippingAddressService.update(id, request);
        return ResponseEntity.ok(ResponseObject.builder()
                .status("OK")
                .message("Updated shipping address successfully")
                .errCode(0)
                .data(updated)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        shippingAddressService.delete(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .status("OK")
                .message("Deleted shipping address successfully")
                .errCode(0)
                .build());
    }
}
