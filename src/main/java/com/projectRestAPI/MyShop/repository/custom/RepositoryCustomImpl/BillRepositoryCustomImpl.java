package com.projectRestAPI.MyShop.repository.custom.RepositoryCustomImpl;

import com.projectRestAPI.MyShop.dto.response.BillPage.BillPageResponse;
import com.projectRestAPI.MyShop.model.Bill;
import com.projectRestAPI.MyShop.repository.custom.BillRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class BillRepositoryCustomImpl implements BillRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public BillPageResponse findBillsByCriteria(Date startDate, Date endDate, String sort, Integer search, Integer status, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill> cq = cb.createQuery(Bill.class);
        Root<Bill> bill = cq.from(Bill.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filter by date range
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(bill.get("createdDate"), startDate));
        }
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(bill.get("createdDate"), endDate));
        }

        // Filter by status
        if (status != null) {
            predicates.add(cb.equal(bill.get("status"), status));
        }

        // Filter by search (using phone, email, or address)
        if (search != null) {
            String searchPattern = "%" + search + "%";
            Predicate searchPredicate = cb.or(
                    cb.like(bill.get("phone"), searchPattern),
                    cb.like(bill.get("email"), searchPattern),
                    cb.like(bill.get("address"), searchPattern)
            );
            predicates.add(searchPredicate);
        }

        // Add all predicates
        cq.where(predicates.toArray(new Predicate[0]));

        // Sorting
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "Date:ASC" -> cq.orderBy(cb.asc(bill.get("createdDate")));
                case "Date:DESC" -> cq.orderBy(cb.desc(bill.get("createdDate")));
                case "Price:ASC" -> cq.orderBy(cb.asc(bill.get("total_price")));
                case "Price:DESC" -> cq.orderBy(cb.desc(bill.get("total_price")));
            }
        }

        TypedQuery<Bill> query = entityManager.createQuery(cq);

        // Pagination
        int totalRows = query.getResultList().size();
        List<Bill> bills = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        Page<Bill> billPage = new PageImpl<>(bills, pageable, totalRows);

        return new BillPageResponse(billPage, totalRows);
    }
}
