package com.example.quizapp.lesson.condition;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
//@ConditionalOnMissingBean(value = DebugService.class)
//@ConditionalOnBean(value = DebugService.class)
//@ConditionalOnClass(value = Other.class)
//@ConditionalOnProperty(value = "custom.number", havingValue = "3", matchIfMissing = true)
@ConditionalOnMinute(value = 17)
//@ConditionalOnMinute2
public class ThirdService {

    @PostConstruct
    public void pc() {
        System.out.println(ThirdService.class + " initialized");
    }

}
