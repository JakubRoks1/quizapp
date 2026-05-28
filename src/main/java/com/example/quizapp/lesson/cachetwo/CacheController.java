package com.example.quizapp.lesson.cachetwo;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheManager cacheManager;
    private final CacheService cacheService;


    @GetMapping()
    public ResponseEntity<?> cache() {
        var randomBook = cacheService.getRandomBook();
        System.out.println(randomBook);

        return ResponseEntity.ok(randomBook);
    }
}
