package com.pm.brokeragefirm.service;

import com.pm.brokeragefirm.dto.request.CreateAssetRequest;
import com.pm.brokeragefirm.dto.response.AssetResponse;
import com.pm.brokeragefirm.entity.Asset;
import com.pm.brokeragefirm.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    @Transactional(readOnly = true)
    public List<AssetResponse> listForCustomer(Long customerId) {
        return assetRepository.findByCustomerId(customerId)
                .stream().map(a -> new AssetResponse(a.getAssetName(), a.getSize(), a.getUsableSize())).toList();
    }

    @Transactional
    public AssetResponse createAsset(Long customerId, CreateAssetRequest request) {

        Asset newAsset = new Asset();
        newAsset.setCustomerId(customerId);
        newAsset.setAssetName(request.assetName());
        newAsset.setSize(request.size());
        newAsset.setUsableSize(request.size());

        Asset savedAsset = assetRepository.save(newAsset);

        return new AssetResponse(
                savedAsset.getAssetName(),
                savedAsset.getSize(),
                savedAsset.getUsableSize()
        );
    }
}
