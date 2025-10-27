package com.pm.brokeragefirm.controller;

import com.pm.brokeragefirm.dto.request.CreateAssetRequest;
import com.pm.brokeragefirm.dto.response.AssetResponse;
import com.pm.brokeragefirm.service.AssetService;
import com.pm.brokeragefirm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;
    private final CustomerService customerService;

    private Long customerIdFromAuth(Authentication auth) {
        String username = auth.getName();
        return customerService.getIdByUsername(username);
    }

    @GetMapping
    public List<AssetResponse> list(Authentication auth) {
        return assetService.listForCustomer(customerIdFromAuth(auth));
    }

    @PostMapping
    public ResponseEntity<AssetResponse> create(@RequestBody CreateAssetRequest request, Authentication auth) {
        Long customerId = customerIdFromAuth(auth);

        AssetResponse created = assetService.createAsset(customerId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
