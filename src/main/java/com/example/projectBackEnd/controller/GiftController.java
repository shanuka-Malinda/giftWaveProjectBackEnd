package com.example.projectBackEnd.controller;

import com.example.projectBackEnd.dto.GiftDto;
import com.example.projectBackEnd.entity.Gift;
import com.example.projectBackEnd.service.GiftService;
import com.example.projectBackEnd.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import java.util.List;

@RestController
@RequestMapping("/api/gift")
public class GiftController {
    private final GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @PostMapping("/create")
    public CommonResponse createGift(@RequestBody GiftDto giftDto){
        return giftService.createGift(giftDto);
    }
    @GetMapping("/getAll")
    public CommonResponse getAllGift(){
        return giftService.getAllGift();
    }
    @GetMapping("/get")
    public List<Gift> get(){
        return giftService.getGift();
    }
}
