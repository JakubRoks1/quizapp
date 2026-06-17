package cachetwo;

import com.example.quizapp.lesson.cachetwo.CacheMain2;
import com.example.quizapp.lesson.cachetwo.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = CacheMain2.class)
class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void clearCache() {
        cacheManager.getCache("books").clear();
    }

    @Test
    void givenBookId_whenGetRandomBook2_thenShouldPutBookIntoCache() {
        cacheService.getRandomBook2(1);

        assertNotNull(cacheManager.getCache("books").get("abc-1"));
    }

    @Test
    void givenCachedBook_whenEvictBookFromCache_thenShouldEvictBookFromCache() {
        cacheService.getRandomBook2(1);

        assertNotNull(cacheManager.getCache("books").get("abc-1"));

        cacheService.evictBookFromCache(1);

        assertNull(cacheManager.getCache("books").get("abc-1"));
    }

    @Test
    void givenBookId_whenUpdateBookInCache_thenShouldUpdateBookInCache() {
        cacheService.updateBookInCache(1);

        Cache.ValueWrapper value = cacheManager.getCache("books").get("abc-1");

        assertNotNull(value);
        assertEquals("Updated book 1", value.get());
    }
}
