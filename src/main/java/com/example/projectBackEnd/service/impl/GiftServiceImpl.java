package com.example.projectBackEnd.service.impl;

import com.example.projectBackEnd.constant.CommonMsg;
import com.example.projectBackEnd.constant.CommonStatus;
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
            Gift gift = castGiftDtoToEntity(giftDto);
            gift = giftRepo.save(gift);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(gift));
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

    private Gift castGiftDtoToEntity(GiftDto giftDto) {
        Gift gift = new Gift();
        gift.setGiftName(giftDto.getGiftName());
        gift.setCreatedAt(LocalDateTime.now());
        gift.setCommonStatus(giftDto.getCommonStatus());
        gift.setUserId(giftDto.getUserId());
        Set<Items> items = giftDto.getItemIds().stream()
                .map(itemId -> itemsRepo.findById(itemId)
                        .orElseThrow(() -> new RuntimeException("Item not found")))
                .collect(Collectors.toSet());
        gift.setItems(items);

//        User user = userRepo.findById(giftDto.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + giftDto.getUserId()));
//
//        gift.setUser(user);

        return gift;
    }
    private GiftDto castEntityToDto(Gift gift) {
        GiftDto giftDto = new GiftDto();
        giftDto.setId(gift.getId());
        giftDto.setGiftName(gift.getGiftName());
        giftDto.setSendingDate(gift.getSendingDate());
        giftDto.setCreatedAt(String.valueOf(gift.getCreatedAt()));
        giftDto.setCommonStatus(gift.getCommonStatus());
        giftDto.setUserId(gift.getUserId());
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
