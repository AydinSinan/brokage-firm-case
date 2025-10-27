package com.pm.brokeragefirm.service;

import com.pm.brokeragefirm.entity.Asset;
import com.pm.brokeragefirm.exception.BusinessRuleException;
import com.pm.brokeragefirm.repository.AssetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountingService {
    private final AssetRepository assetRepository;

    @Transactional
    public void reserveForBuy(Long customerId, String assetToBuy, BigDecimal size, BigDecimal price) {
        BigDecimal requiredTry = size.multiply(price);
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
                .orElseThrow(() -> new BusinessRuleException("TRY asset not found"));
        if (tryAsset.getUsableSize().compareTo(requiredTry) < 0)
            throw new BusinessRuleException("Insufficient TRY usable balance");
        tryAsset.setUsableSize(tryAsset.getUsableSize().subtract(requiredTry));
        assetRepository.save(tryAsset);
    }

    @Transactional
    public void reserveForSell(Long customerId, String assetName, BigDecimal size) {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, assetName)
                .orElseThrow(() -> new BusinessRuleException("Asset not found: " + assetName));
        if (asset.getUsableSize().compareTo(size) < 0)
            throw new BusinessRuleException("Insufficient asset usable balance");
        asset.setUsableSize(asset.getUsableSize().subtract(size));
        assetRepository.save(asset);
    }

    @Transactional
    public void releaseOnCancelBuy(Long customerId, BigDecimal size, BigDecimal price) {
        BigDecimal refundTry = size.multiply(price);
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
                .orElseThrow(() -> new BusinessRuleException("TRY asset not found"));
        tryAsset.setUsableSize(tryAsset.getUsableSize().add(refundTry));
        assetRepository.save(tryAsset);
    }

    @Transactional
    public void releaseOnCancelSell(Long customerId, String assetName, BigDecimal size) {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, assetName)
                .orElseThrow(() -> new BusinessRuleException("Asset not found: " + assetName));
        asset.setUsableSize(asset.getUsableSize().add(size));
        assetRepository.save(asset);
    }

    /** Admin match: settle TRY and asset sizes+usable */
    @Transactional
    public void settleOnMatch(Long customerId, String assetName, BigDecimal size, BigDecimal price, boolean isBuy) {
        if (isBuy) {
            Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
                    .orElseThrow(() -> new BusinessRuleException("TRY asset not found"));
            BigDecimal cost = size.multiply(price);
            tryAsset.setSize(tryAsset.getSize().subtract(cost));
            assetRepository.save(tryAsset);


            Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, assetName)
                    .orElseGet(() -> Asset.builder().customerId(customerId).assetName(assetName)
                            .size(BigDecimal.ZERO).usableSize(BigDecimal.ZERO).build());
            asset.setSize(asset.getSize().add(size));
            asset.setUsableSize(asset.getUsableSize().add(size));
            assetRepository.save(asset);
        } else {
            Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, assetName)
                    .orElseThrow(() -> new BusinessRuleException("Asset not found: " + assetName));
            asset.setSize(asset.getSize().subtract(size));
            assetRepository.save(asset);


            Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY")
                    .orElseThrow(() -> new BusinessRuleException("TRY asset not found"));
            BigDecimal income = size.multiply(price);
            tryAsset.setSize(tryAsset.getSize().add(income));
            tryAsset.setUsableSize(tryAsset.getUsableSize().add(income));
            assetRepository.save(tryAsset);
        }
    }




}
