package com.projectRestAPI.MyShop.service.Impl.Product;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.ColorRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.ColorMapper;
import com.projectRestAPI.MyShop.mapper.ColorMapper;
import com.projectRestAPI.MyShop.model.BaseEntity;
import com.projectRestAPI.MyShop.model.SanPham.Color;
import com.projectRestAPI.MyShop.model.SanPham.Color;
import com.projectRestAPI.MyShop.repository.ColorRepository;
import com.projectRestAPI.MyShop.service.ColorService;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl extends BaseServiceImpl<Color,Long, ColorRepository> implements ColorService {
    @Autowired
    private ColorMapper colorMapper;
    @Override
    public ResponseEntity<ResponseObject> add(ColorRequest colorRequest) {
        Color color = colorMapper.toColor(colorRequest);
        return createNew(color);
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<Color> colorOptional = findById(id);
        if(colorOptional.isEmpty()){
            throw new AppException(ErrorCode.SIZE_NOT_FOUND);
        }
        Color color = colorOptional.get();
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,colorMapper.toColorResponse(color)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> update(ColorRequest colorRequest) {
        Optional<Color> colorOptional = findById(colorRequest.getId());
        if (colorOptional.isEmpty()) {
            throw new AppException(ErrorCode.SIZE_NOT_FOUND);
        }

        Color color = colorMapper.toColor(colorRequest);

        Color updatedColor = repository.save(color);

        return new ResponseEntity<>(new ResponseObject(
                "Success",
                "Sửa dữ liệu thành công",
                200,
                colorMapper.toColorResponse(updatedColor)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }
}
