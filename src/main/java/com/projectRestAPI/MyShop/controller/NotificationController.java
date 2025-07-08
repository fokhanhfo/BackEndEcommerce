package com.projectRestAPI.MyShop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Notification.Notification;
import com.projectRestAPI.MyShop.repository.NotificationRepository;
import com.projectRestAPI.MyShop.service.NotificationService;
import com.projectRestAPI.MyShop.service.ProductService;
import com.projectRestAPI.MyShop.utils.SearchCriteriaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                    @RequestParam(value = "sort",defaultValue = "") String sort,
                                    @RequestParam(value = "limit",defaultValue = "12") int limit) throws JsonProcessingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Pageable pageable = PageRequest.of(page,limit);
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if (roles.contains("ROLE_ADMIN") || roles.contains("ROLE_STAFF")) {
            criteriaList.add(new SearchCriteria("recipient", ":", "admin"));
        } else {
            criteriaList.add(new SearchCriteria("recipient", ":", username));
        }
        List<String> sortParams;
        if (sort == null || sort.trim().isEmpty()) {
            sortParams = Collections.singletonList("id");
        } else {
            sortParams = Arrays.asList(sort.split(","));
        }
        return notificationService.getAll(criteriaList,pageable, sortParams);
    }

    public void sendNotification(String recipient, Notification notification) {
        messagingTemplate.convertAndSend("/topic/notifications/" + recipient, notification);
    }

//    @GetMapping
//    public ResponseEntity<?> getNotificationsForCurrentUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        List<Notification> notifications = notificationRepository.findByRecipient(username);
//        ResponseObject responseObject = ResponseObject.builder()
//                .status("Success")
//                .message("Lấy dữ liệu thành công")
//                .errCode(200)
//                .data(new HashMap<String,Object>(){{
//                    put("notifications",notifications);
//                    put("count",response.getTotalElements());
//                }})
//                .build();
//        return new ResponseEntity<>(responseObject, HttpStatus.OK);
//    }
}
