package com.ssafy.ssap.service;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.domain.qna.Answer;
import com.ssafy.ssap.domain.qna.Likes;
import com.ssafy.ssap.domain.qna.Question;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.AnswerCreateDto;
import com.ssafy.ssap.dto.AnswerResponseDto;
import com.ssafy.ssap.dto.LikesDto;
import com.ssafy.ssap.repository.AnswerRepository;
import com.ssafy.ssap.repository.LikesRepository;
import com.ssafy.ssap.repository.QuestionRepository;
import com.ssafy.ssap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public Integer create(AnswerCreateDto answersCreateDto) throws Exception {
        User user = userRepository.getReferenceById(answersCreateDto.getUserNo());

        Question question = questionRepository.findById(answersCreateDto.getQuestionNo())
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_QUETION_ID));

        Answer answer = Answer.builder()
                .detail(answersCreateDto.getContent())
                .user(user)
                .question(question)
                .registTime(LocalDateTime.now())
                .build();

        answerRepository.save(answer);

        return answer.getId();
    }

    @Transactional
    public Integer update(Integer answerNo, AnswerCreateDto answerCreateDto) throws Exception {
        Answer answer = answerRepository.findById(answerNo)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_ANSWER_ID));
        return answer.update(answerCreateDto.getContent());
    }

	public List<AnswerResponseDto> getList(Integer questionNo) {
        questionRepository.findById(questionNo)
            .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_QUETION_ID));

        return answerRepository.findByQuestionId(questionNo);
	}

    public void updateLikes(Integer answerNo, LikesDto likesDto) {
        Answer answer = answerRepository.findById(answerNo)
            .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_ANSWER_ID));

        User user = userRepository.findById(likesDto.getUserNo())
            .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_USER_ID));

        Likes likes = Likes.builder()
            .answer(answer)
            .isGood(likesDto.getIsLike())
            .user(user)
            .build();

        likesRepository.save(likes);
    }
}
