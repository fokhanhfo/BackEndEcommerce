package com.projectRestAPI.MyShop.controller.Discount;

import com.projectRestAPI.MyShop.dto.request.Discount.DiscountRequest;
import com.projectRestAPI.MyShop.dto.response.Discount.DiscountResponse;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.Discount.DiscountMapper;
import com.projectRestAPI.MyShop.model.Discount.Discount;
import com.projectRestAPI.MyShop.repository.Discount.DiscountRepository;
import com.projectRestAPI.MyShop.service.DiscountService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.TypeToken;

import java.util.List;

@RestController
@RequestMapping("/discount")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private DiscountMapper discountMapper;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    private ResponseEntity<ResponseObject> add(@RequestBody @Valid DiscountRequest discountRequest){
        return discountService.add(discountRequest);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Discount> discounts = discountRepository.findAll();
        List<DiscountResponse> discountResponse = discountMapper.toListDiscountResponse(discounts);
        ResponseObject responseObject = ResponseObject.builder()
                .status("success")
                .errCode(200)
                .message("Lấy dữ liệu thành công")
                .data(discountResponse)
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable("id") Long id){
        return discountService.getId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return discountService.delete(id);
    }
}
