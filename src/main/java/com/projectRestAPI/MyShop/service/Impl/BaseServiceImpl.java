package com.projectRestAPI.MyShop.service.Impl;

import com.projectRestAPI.MyShop.Exception.AppException;
import com.projectRestAPI.MyShop.Exception.ErrorCode;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.BaseEntity;
import com.projectRestAPI.MyShop.model.Category;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.repository.BaseRepository;
import com.projectRestAPI.MyShop.repository.EntitySpecification;
import com.projectRestAPI.MyShop.repository.UsersRepository;
import com.projectRestAPI.MyShop.service.BaseService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class BaseServiceImpl<E extends BaseEntity,ID extends Serializable,R extends BaseRepository<E,ID>> implements BaseService<E,ID> {
    protected R repository;

    @Autowired
    protected UsersRepository usersRepository;

    @Autowired
    public void setRepository(R repository) {
        this.repository = repository;
    }
    @Override
    public ResponseEntity<ResponseObject> createNew(E entity) {
        repository.save(entity);
        return new ResponseEntity<>(new ResponseObject("success", "Thêm Mới Thành Công", 0, entity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseObject> update(E entity) {
        if (entity != null) {
            repository.save(entity);
            return new ResponseEntity<>(new ResponseObject("success", "Cập Nhật Thành Công", 0, entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseObject("error", "Đối tượng không hợp lệ", 1, null), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> delete(ID id) {
        Optional<E> otp = findById(id);
        if (otp.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("error", "Không Tìm Thấy ID",1,null), HttpStatus.BAD_REQUEST);
        }

        repository.deleteById(id);
        return new ResponseEntity<>(new ResponseObject("success", "Đã Xóa Thành Công", 0, otp.get()), HttpStatus.OK);
    }

    @Override
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public Boolean existsById(ID id) {
        return null;
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }


//    get all but error
    @Override
    public Page<E> getAll(List<SearchCriteria> params, Pageable pageable, List<String> sort,@Nullable Users users) {

        EntitySpecification<E> specification = new EntitySpecification<>();

        if(users != null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            if(!isAdmin){
                SearchCriteria userCriteria = new SearchCriteria("user", ":", users);
                params.add(userCriteria);
            }
        }

        params.forEach(specification::add);

        Sort sortCriteria = Sort.unsorted();

        if (!sort.isEmpty()) {
            List<Sort> sortList = new ArrayList<>();

            boolean hasDiscountPresent = false;

            for (String s : sort) {
                if ("hasDiscount".equals(s)) {
                    hasDiscountPresent = true;
                    continue; // xử lý riêng ở ngoài
                }

                String[] parts = s.split(":");
                String field = parts[0];
                Sort.Direction direction = (parts.length > 1 && "DESC".equalsIgnoreCase(parts[1]))
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

                if (!"id".equals(field)) {
                    sortList.add(Sort.by(direction, field));
                }
            }

            // Thêm hasDiscount vào đầu (nếu có)
            if (hasDiscountPresent) {
                sortList.add(0, Sort.by(Sort.Order.desc("productDiscountPeriods.id")));
            }

            // ✅ Luôn thêm id DESC vào CUỐI
            sortList.add(Sort.by(Sort.Order.desc("id")));

            // Gộp lại tất cả
            sortCriteria = sortList.stream()
                    .reduce(Sort::and)
                    .orElse(Sort.unsorted());
        }


//        for (String s : sort) {
//            if ("hasDiscount".equals(s)) {
//                // Ưu tiên sản phẩm có giảm giá (sản phẩm nào có join với DiscountPeriod thì lên trước)
//                sortCriteria = sortCriteria.and(Sort.by(Sort.Order.desc("productDiscountPeriods.id")));
//            } else {
//                String[] parts = s.split(":");
//                String field = parts[0];
//                Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("DESC"))
//                        ? Sort.Direction.DESC
//                        : Sort.Direction.ASC;
//                sortCriteria = sortCriteria.and(Sort.by(direction, field));
//            }
//        }



        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortCriteria);

        Page<E> result = repository.findAll(specification, pageable);

        return  result;
    }


}
