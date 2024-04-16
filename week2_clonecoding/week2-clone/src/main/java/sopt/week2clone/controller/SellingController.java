package sopt.week2clone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.week2clone.service.SellingService;
import sopt.week2clone.service.dto.SellingCreateDto;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home/post/sell")
public class SellingController {

    private final SellingService sellingService;

    @PostMapping("")
    public ResponseEntity createSelling(@RequestBody SellingCreateDto sellingCreateDto) {
        return ResponseEntity.created(URI.create(sellingService.createSelling(sellingCreateDto))).build();
    }
}
