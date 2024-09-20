package com.example.projectBackEnd.service.impl;

import com.example.projectBackEnd.constant.CommonMsg;
import com.example.projectBackEnd.constant.CommonStatus;
import com.example.projectBackEnd.constant.PaymentStatus;
import com.example.projectBackEnd.dto.GiftDto;
import com.example.projectBackEnd.entity.Gift;
import com.example.projectBackEnd.entity.Items;
import com.example.projectBackEnd.entity.User;
import com.example.projectBackEnd.repo.GiftRepo;
import com.example.projectBackEnd.repo.ItemsRepo;
import com.example.projectBackEnd.repo.UserRepo;
import com.example.projectBackEnd.service.GiftService;
import com.example.projectBackEnd.util.CommonResponse;
import com.example.projectBackEnd.util.CommonValidation;
import org.hibernate.tool.schema.SchemaToolingLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public class GiftServiceImpl implements GiftService {

    private final GiftRepo giftRepo;
    private final ItemsRepo itemsRepo;

    private final UserRepo userRepo;

    @Autowired
    public GiftServiceImpl(GiftRepo giftRepo, ItemsRepo itemsRepo, UserRepo userRepo) {
        this.giftRepo = giftRepo;
        this.itemsRepo = itemsRepo;
        this.userRepo = userRepo;
    }

    @Override
    public CommonResponse createGift(GiftDto giftDto) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<String> validationList = giftValidation(giftDto);
            if (!validationList.isEmpty()){
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            Gift gift = castGiftDtoToEntity(giftDto);
            gift = giftRepo.save(gift);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(gift.getId()));
        } catch (Exception e) {
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while saving the gift."));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAllGift() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<Object> giftDtoList = giftRepo.findAll().stream()
                    .filter(Gift -> Gift.getCommonStatus() == CommonStatus.ACTIVE)
                    .map(this::castEntityToDto)
                    .collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(giftDtoList);  // Directly set the list
        } catch (Exception e) {
            SchemaToolingLogging.LOGGER.error("/**************** Exception in GiftService -> getAllGifts()", e);
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while fetching gifts."));
        }
        return commonResponse;
    }

    @Override
    public List<Gift> getGift() {
        return giftRepo.findAll();
    }

    @Override
    public CommonResponse updatePaymentStatus(GiftDto giftDto) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            if (giftDto.getId() == null) {
                commonResponse.setStatus(false);
                commonResponse.setErrorMessages(Collections.singletonList("Gift ID is required for update."));
                return commonResponse;
            }

            Gift existingGiftBox = giftRepo.findById(Long.valueOf(giftDto.getId()))
                    .orElseThrow(() -> new RuntimeException("Gift Box not found"));

            existingGiftBox.setPaymentStatus(giftDto.getPaymentStatus());


            giftRepo.save(existingGiftBox);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList("Payment SuccessFully"));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in GiftService -> updateProduct()", e);
            commonResponse.setStatus(false);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating the gift payment."));
        }
        return commonResponse;
    }

    private Gift castGiftDtoToEntity(GiftDto giftDto) {
        Gift gift = new Gift();
        gift.setGiftName(giftDto.getGiftName());
        gift.setCreatedAt(giftDto.getCreatedAt());
        gift.setCommonStatus(giftDto.getCommonStatus());
        gift.setUserId(giftDto.getUserId());
        gift.setRecieverAddress(giftDto.getRecieverAddress());
        gift.setSendingDate(giftDto.getSendingDate());
        gift.setZip(giftDto.getZip());
        gift.setTotalPrice(giftDto.getTotalPrice());
        gift.setPaymentStatus(PaymentStatus.NOT_PAID);
        Set<Items> items = giftDto.getItemIds().stream()
                .map(itemId -> itemsRepo.findById(itemId)
                        .orElseThrow(() -> new RuntimeException("Item not found")))
                .collect(Collectors.toSet());
        gift.setItems(items);

        return gift;
    }
    private GiftDto castEntityToDto(Gift gift) {
        GiftDto giftDto = new GiftDto();
        giftDto.setId(gift.getId());
        giftDto.setGiftName(gift.getGiftName());
        giftDto.setSendingDate(gift.getSendingDate());
        giftDto.setCreatedAt(gift.getCreatedAt());
        giftDto.setCommonStatus(gift.getCommonStatus());
        giftDto.setUserId(gift.getUserId());
        giftDto.setRecieverAddress(gift.getRecieverAddress());
        giftDto.setZip(gift.getZip());
        giftDto.setItemIds(gift.getItems().stream()
                .map(Items::getId)
                .collect(Collectors.toSet()));
        return giftDto;
    }


    private List<String> giftValidation(GiftDto giftDto) {
        List<String> validationList = new ArrayList<>();

        if (CommonValidation.stringNullValidation(giftDto.getGiftName())) {
            validationList.add(CommonMsg.EMPTY_GIFT_NAME);
        }

        return validationList;
    }
}
