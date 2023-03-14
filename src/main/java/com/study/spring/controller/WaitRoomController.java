package com.study.spring.controller;

import com.study.spring.domain.valid.WaitValidGroups;
import com.study.spring.dto.*;
import com.study.spring.service.WaitRoomService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class WaitRoomController {

    private final WaitRoomService waitRoomService;

    public WaitRoomController(WaitRoomService waitRoomService) {
        this.waitRoomService = waitRoomService;
    }

    @PostMapping("/waitRoom/card/{cardType}")
    public MessageResponseDto cardArrangement(@Validated(WaitValidGroups.Card.class) @RequestBody WaitRequestDto waitRequestDto,
                                              @PathVariable String cardType) {
        return waitRoomService.cardArrangement(waitRequestDto, cardType);
    }

    @PostMapping("/waitRoom/costume")
    public MessageResponseDto costumeArrangement(@Validated(WaitValidGroups.Custom.class) @RequestBody WaitRequestDto waitRequestDto) {
        return waitRoomService.costumeArrangement(waitRequestDto);
    }

    //TODO /member/costume

}
