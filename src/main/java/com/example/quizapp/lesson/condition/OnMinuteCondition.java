package com.example.quizapp.lesson.condition;

import lombok.val;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.time.LocalDateTime;

public class OnMinuteCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        val value = (Integer) metadata.getAllAnnotationAttributes(ConditionalOnMinute.class.getName()).get("value").get(0);

        return LocalDateTime.now().getMinute() == value;
    }


    public static class MyNestedMinuteCondition extends AnyNestedCondition {

        public MyNestedMinuteCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnMinute(14)
        static class M12 { }

        @ConditionalOnMinute(15)
        static class M13 { }
    }

}
