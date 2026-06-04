package com.example.quizapp.lesson.cachetwo;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> cache(@PathVariable("id") Integer id) {
        var randomBook = cacheService.getRandomBook2(id);;

        return ResponseEntity.ok(randomBook);
    }

    @GetMapping("/v2/{id}")
    public ResponseEntity<?> cache2(@PathVariable("id") Integer id) {
        var randomBook = cacheService.getRandomBook3(id);
//        cacheManager.getCacheNames().size()
//        cacheManager.getCache("books").

        return ResponseEntity.ok(randomBook);
    }
}
