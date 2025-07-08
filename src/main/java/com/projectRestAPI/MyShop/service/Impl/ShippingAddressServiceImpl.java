package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.ShippingAddressRequest;
import com.projectRestAPI.MyShop.dto.response.ShippingAddressResponse;
import com.projectRestAPI.MyShop.model.ShippingAddress;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.ShippingAddressRepository;
import com.projectRestAPI.MyShop.repository.UsersRepository;
import com.projectRestAPI.MyShop.service.ShippingAddressService;
import com.projectRestAPI.MyShop.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private  UsersRepository userRepository;

    @Autowired
    private UsersService usersService;

    public ShippingAddressResponse create(ShippingAddressRequest request) {
        Users users =usersService.getUser();

        // kiểm tra user đã có bao nhiêu địa chỉ
        long count = shippingAddressRepository.countByUserId(users.getId());
        if (count >= 4) {
            throw new AppException(ErrorCode.MAX_SHIPPING_ADDRESS_REACHED);
        }

        ShippingAddress address = ShippingAddress.builder()
                .recipientName(request.getRecipientName())
                .phone(request.getPhone())
                .idProvince(request.getIdProvince())
                .idDistrict(request.getIdDistrict())
                .idCommune(request.getIdCommune())
                .addressDetail(request.getAddressDetail())
                .user(users)
                .build();

        ShippingAddress saved = shippingAddressRepository.save(address);

        return toResponse(saved);
    }


    public List<ShippingAddressResponse> getAll() {
        Users user = usersService.getUser();
        return shippingAddressRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    public ShippingAddressResponse getById(Long id) {
        ShippingAddress address = shippingAddressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ShippingAddress_NOT_FOUND));
        return toResponse(address);
    }

    @Transactional
    public ShippingAddressResponse update(Long id, ShippingAddressRequest request) {
        Users currentUser = usersService.getUser();

        ShippingAddress address = shippingAddressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ShippingAddress_NOT_FOUND));

        if (!address.getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // chỉ update field nào thực sự được truyền
        if (request.getRecipientName() != null) {
            address.setRecipientName(request.getRecipientName());
        }
        if (request.getPhone() != null) {
            address.setPhone(request.getPhone());
        }
        if (request.getAddressDetail() != null) {
            address.setAddressDetail(request.getAddressDetail());
        }
        if (request.getIdProvince() != null) {
            address.setIdProvince(request.getIdProvince());
        }
        if (request.getIdDistrict() != null) {
            address.setIdDistrict(request.getIdDistrict());
        }
        if (request.getIdCommune() != null) {
            address.setIdCommune(request.getIdCommune());
        }

        // xử lý mặc định
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            shippingAddressRepository.resetDefaultForUser(currentUser.getId());
            address.setIsDefault(true);
        }

        ShippingAddress updated = shippingAddressRepository.save(address);

        return toResponse(updated);
    }


    public void delete(Long id) {
        Users currentUser = usersService.getUser();

        ShippingAddress address = shippingAddressRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ShippingAddress_NOT_FOUND));

        if (!address.getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        shippingAddressRepository.delete(address);
    }


    private ShippingAddressResponse toResponse(ShippingAddress address) {
        ShippingAddressResponse response = new ShippingAddressResponse();
        response.setId(address.getId());
        response.setRecipientName(address.getRecipientName());
        response.setIdProvince(address.getIdProvince());
        response.setIdDistrict(address.getIdDistrict());
        response.setIdCommune(address.getIdCommune());
        response.setPhone(address.getPhone());
        response.setAddressDetail(address.getAddressDetail());
        response.setUserId(address.getUser().getId());
        response.setIsDefault(address.getIsDefault());
        return response;
    }
}
