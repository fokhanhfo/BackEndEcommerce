package com.projectRestAPI.MyShop.service.Impl.Product;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.request.SizeRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.mapper.SizeMapper;
import com.projectRestAPI.MyShop.model.SanPham.Color;
import com.projectRestAPI.MyShop.model.SanPham.Size;
import com.projectRestAPI.MyShop.repository.SizeRepository;
import com.projectRestAPI.MyShop.service.Impl.BaseServiceImpl;
import com.projectRestAPI.MyShop.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl extends BaseServiceImpl<Size,Long, SizeRepository> implements SizeService {
    @Autowired
    private SizeMapper sizeMapper;

    @Override
    public ResponseEntity<ResponseObject> getAll() {
        List<Size> sizes = findAll();
        return new ResponseEntity<>(new ResponseObject(
                "Succes",
                "Lấy dữ liệu thành công",
                200,sizes)
                , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> add(SizeRequest sizeRequest) {
        Size size = sizeMapper.toSize(sizeRequest);
        return createNew(size);
    }

    @Override
    public ResponseEntity<ResponseObject> getId(Long id) {
        Optional<Size> sizeOptional = findById(id);
        if(sizeOptional.isEmpty()){
            throw new AppException(ErrorCode.SIZE_NOT_FOUND);
        }
        Size size = sizeOptional.get();
        return new ResponseEntity<>(new ResponseObject("Succes","Lấy dữ liệu thành công",200,sizeMapper.toSizeResponse(size)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseObject> update(SizeRequest sizeRequest) {
        Optional<Size> sizeOptional = findById(sizeRequest.getId());
        if (sizeOptional.isEmpty()) {
            throw new AppException(ErrorCode.SIZE_NOT_FOUND);
        }

        Size size = sizeMapper.toSize(sizeRequest);

        Size updatedSize = repository.save(size);

        return new ResponseEntity<>(new ResponseObject(
                "Success",
                "Sửa dữ liệu thành công",
                200,
                sizeMapper.toSizeResponse(updatedSize)), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResponseObject> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort) {
        return null;
    }
}
