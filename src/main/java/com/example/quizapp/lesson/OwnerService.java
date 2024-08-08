package com.example.quizapp.lesson;

import jakarta.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final DogRepository dogRepository;

    public OwnerService(OwnerRepository ownerRepository, DogRepository dogRepository) {
        this.ownerRepository = ownerRepository;
        this.dogRepository = dogRepository;
    }

    public void create() {
        var owner = new Owner();
        owner.setName("Adam");

        ownerRepository.save(owner);
    }

    @Transactional
    void trans() {
        val owner = ownerRepository.findById(1L).get();
        System.out.println(owner);
        owner.setName("Zosia");
    }

    void m3() {
        trans();
    }

    void m4() {
        Owner o = new Owner();
        o.setName("Psiarz-2");

        Dog d1 = new Dog();
        d1.setType("Jamnik-2");

        Dog d2 = new Dog();
        d2.setType("Spaniel-2");

//        d1 = dogRepository.save(d1);
//        d2 = dogRepository.save(d2);

        o.setDogs(List.of(d1,d2));

        ownerRepository.save(o);
    }

    void m5() {
        var byId = ownerRepository.findById(1L);
        System.out.println(byId.get());
    }

}
