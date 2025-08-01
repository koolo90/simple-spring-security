package com.example.bussinsess;

import com.example.buisseness.Answer;
import com.example.buisseness.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestAnswerShould {
    @Mock Question questionMock;

    @InjectMocks Answer answer;

    @Test
    void haveQuestion() {
        answer.setQuestion(questionMock);

        assertThat(answer.getQuestion()).isNotNull().isEqualTo(questionMock);
    }

    @Test
    void haveCorrectAnswer() {
        when(questionMock.getSuggestedAnswer()).thenReturn("Foo");
        answer.setQuestion(questionMock);
        answer.setContent("Foo");

        assertThat(answer.getContent()).isNotNull().isEqualTo("Foo");
        assertThat(answer.isCorrect()).isTrue();
    }

    @Test
    void haveIncorrectAnswer() {
        when(questionMock.getSuggestedAnswer()).thenReturn("Foo");
        answer.setQuestion(questionMock);
        answer.setContent("Bar");

        assertThat(answer.getContent()).isNotNull().isEqualTo("Foo");
        assertThat(answer.isCorrect()).isFalse();
    }



}
