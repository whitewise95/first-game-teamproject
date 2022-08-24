package com.study.spring.controller;

import com.study.spring.dto.*;
import com.study.spring.service.WaitRoomService;
import org.springframework.web.bind.annotation.*;

@RestController
public class WaitRoomController {

    private final WaitRoomService waitRoomService;

    public WaitRoomController(WaitRoomService waitRoomService) {
        this.waitRoomService = waitRoomService;
    }

    @PostMapping("/waitRoom/card/{cardType}")
    public MessageResponseDto cardArrangement(@RequestBody WaitRequestDto waitRequestDto,
                                              @PathVariable String cardType) {
        return waitRoomService.cardArrangement(waitRequestDto, cardType);
    }

    @PostMapping("/waitRoom/costume")
    public MessageResponseDto costumeArrangement(@RequestBody WaitRequestDto waitRequestDto) {
        return waitRoomService.costumeArrangement(waitRequestDto);
    }
}
