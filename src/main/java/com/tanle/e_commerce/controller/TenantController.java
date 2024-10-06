package com.tanle.e_commerce.controller;

import com.tanle.e_commerce.dto.TenantDTO;
import com.tanle.e_commerce.request.LoginRequest;
import com.tanle.e_commerce.respone.MessageResponse;
import com.tanle.e_commerce.request.TenantRegisterRequest;
import com.tanle.e_commerce.service.TenantService;
import com.tanle.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tenant/")
public class TenantController extends BaseUserController{
    @Autowired
    private TenantService tenantService;
    @Autowired
    private UserService userService;
    @GetMapping("/{tenantId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")
    private ResponseEntity<TenantDTO> getTenant(@PathVariable int tenantId) {
        TenantDTO tenantDTO = tenantService.findById(tenantId);
        return new ResponseEntity<>(tenantDTO, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('CUSTOMER') or hasAnyAuthority('ADMIN')")
    @PostMapping("/register")
    private ResponseEntity<MessageResponse> registerTenant(@RequestParam TenantRegisterRequest request)  {
        MessageResponse tenant = tenantService.registerInformation(request);
        TenantDTO tenantDTO = (TenantDTO) tenant.getData();
        userService.grantRole(tenantDTO.getUserId(),"SELLER");
        return ResponseEntity.status(HttpStatus.OK).body(tenant);
    }
}
