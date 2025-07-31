package com.example.bussinsess;

import com.example.buisseness.Question;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TestQuestionShould {
    @Test
    void haveContent(){
        Question question = new Question();
        question.setQuestion("What annotations are covered by @SpringBootApplication?");

        Assertions.assertThat(question.getQuestion()).isNotNull()
                .isEqualTo("What annotations are covered by @SpringBootApplication?");
    }

    @Test
    void haveSuggestedAnswer() {
        Question question = new Question();
        question.setSuggestedAnswer("@SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan");

        Assertions.assertThat(question.getSuggestedAnswer()).isNotNull()
                .isEqualTo("@SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan");
    }
}
