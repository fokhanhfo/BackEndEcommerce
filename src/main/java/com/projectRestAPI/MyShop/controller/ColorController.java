package com.projectRestAPI.MyShop.controller;

import com.projectRestAPI.MyShop.dto.request.ColorRequest;
import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.service.ColorService;
import com.projectRestAPI.MyShop.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(){
        return colorService.getAll();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> add(@RequestBody @Valid ColorRequest colorRequest){
        return colorService.add(colorRequest);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<ResponseObject> getId(@PathVariable Long id){
        return colorService.getId(id);
    }

    @PostMapping({"/update"})
    public ResponseEntity<ResponseObject> update(@RequestBody @Valid ColorRequest colorRequest){
        return colorService.update(colorRequest);
    }


}
