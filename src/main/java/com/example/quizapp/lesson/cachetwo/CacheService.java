package com.example.quizapp.lesson.cachetwo;

import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * Samemu poexploruj adnotacje @CachePut @CacheEvict
 * Ew. prezygotuj jakieś testy w Junit + testy w quizApp - to co masz teraz: pobierasz 3 quizy, sprawdzasz ze sa 3 na cacheu, pobierasz 4ty, znika najstarszy, pobierasz najstarszego...
 */
public class CacheService {

    private static final List<String> BOOKS = List.of("Władca pierścieni", "Kajko i kokosz", "Pan Tadeusz", "Dziady", "Romeo i Julia");

    @SneakyThrows
    public String getRandomBook() {

        System.out.println("jestem w metodzie");
        Thread.sleep(2000);
        return "Władca pierścieni";
    }

    @SneakyThrows
    @Cacheable(cacheNames = "books", key = "'abc-' + #p0")
    public String getRandomBook2(int i) {

        System.out.println("jestem w metodzie-2");
        Thread.sleep(2000);
        return BOOKS.get(i);
    }

    @SneakyThrows
    public String getRandomBook3(int i) {

        System.out.println("jestem w metodzie-3");
        Thread.sleep(2000);
        return BOOKS.get(i);
    }

    @CachePut(cacheNames = "books", key = "'abc-' + #p0")
    public String updateBookInCache(int id) {
        System.out.println("Updating book in cache: " + id);
        return "Updated book " + id;
    }

    @CacheEvict(cacheNames = "books", key = "'abc-' + #p0")
    public void evictBookFromCache(int id) {
        System.out.println("Evicting book from cache: " + id);
    }

//    public int bookTitleSize(String book) {
//        return book.length();
//    }
}
