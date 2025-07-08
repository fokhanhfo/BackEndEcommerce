package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(){
        return sizeService.getAll();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> add(@RequestBody @Valid SizeRequest sizeRequest){
        return sizeService.add(sizeRequest);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<ResponseObject> getId(@PathVariable Long id){
        return sizeService.getId(id);
    }

    @PostMapping({"/update"})
    public ResponseEntity<ResponseObject> update(@RequestBody @Valid SizeRequest sizeRequest){
        return sizeService.update(sizeRequest);
    }

}
