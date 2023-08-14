package com.ssafy.ssap.service;

import com.ssafy.ssap.common.MessageFormat;
import com.ssafy.ssap.domain.alarm.Alarm;
import com.ssafy.ssap.domain.qna.Answer;
import com.ssafy.ssap.domain.qna.ArticleImage;
import com.ssafy.ssap.domain.qna.Likes;
import com.ssafy.ssap.domain.qna.Question;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.AnswerCreateDto;
import com.ssafy.ssap.dto.AnswerResponseDto;
import com.ssafy.ssap.dto.LikesDto;
import com.ssafy.ssap.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final LikesRepository likesRepository;
    private final AlarmRepository alarmRepository;

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

        // 사용자에게 알림 전송
        String linkedUrl = "/questiondetail/" + question.getId();

        Alarm alarm = Alarm.builder()
                .title(answer.getUser().getNickname() + " 님이 질문에 댓글을 달았습니다.")
                .detail(answersCreateDto.getContent())
                .isRead(false)
                .linkedUrl(linkedUrl)
                .registTime(LocalDateTime.now())
                .user(question.getUser())
                .build();

        answerRepository.save(answer);
        alarmRepository.save(alarm);

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

        List<Object[]> result = answerRepository.findByQuestionId(questionNo);

        List<AnswerResponseDto> answerResponseDtos = new ArrayList<>();

        Map<Integer, AnswerResponseDto> answerDtoMap = new HashMap<>();

        for (Object[] row : result) {
            Answer a = (Answer) row[0];
            ArticleImage ai = (ArticleImage) row[1];
            AnswerResponseDto current = answerDtoMap.get(a.getId());

            if (current == null) {
                List<String> detailImages = ai != null ? new ArrayList<>(Arrays.asList(ai.getImagePath())) : new ArrayList<>(); // 이미지 경로를 리스트로 만들어서 전달
                AnswerResponseDto answerResponseDto = new AnswerResponseDto(
                        a.getId(),
                        a.getDetail(),
                        a.getRegistTime(),
                        a.getAnswerScore(),
                        a.getQuestion().getAnswer() != null && a.getQuestion().getAnswer().getId().equals(a.getId()),
                        a.getUser().getNickname(),
                        a.getUser().getId(),
                        a.getUser().getProfileImagePath(),
                        detailImages
                );
                answerDtoMap.put(a.getId(), answerResponseDto);
            } else {
                if (ai != null) {
                    current.getDetailImage().add(ai.getImagePath());
                }
            }
        }

        answerResponseDtos.addAll(answerDtoMap.values());
        return answerResponseDtos;
    }


    @Transactional
    public void updateLikes(Integer answerNo, LikesDto likesDto) {
        Answer answer = answerRepository.findById(answerNo)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_ANSWER_ID));

        User user = userRepository.findById(likesDto.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_USER_ID));

        Likes curLikes = likesRepository.findByAnswerIdAndUserId(answerNo, likesDto.getUserNo());

        // 시나리오 1: 존재하지 않는 평가일 때 새로운 평가 생성
        if (curLikes == null) {
            Likes likes = Likes.builder()
                    .answer(answer)
                    .isGood(likesDto.getIsLike())
                    .user(user)
                    .build();

            likesRepository.save(likes);
        }
        // 시나리오 2: 같은 평가일 때 평가 취소
        else if (curLikes.getIsGood().equals(likesDto.getIsLike())) {
            likesRepository.deleteById(curLikes.getId());
        }
        // 시나리오 3: 반대 평가일 때 평가 수정
        else {
            curLikes.updateIsGood(likesDto.getIsLike());
            likesRepository.save(curLikes);
        }
    }

    @Transactional
    public void selectAnswer(Integer questionNo, Integer answerNo) {
        Question question = questionRepository.findById(questionNo)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_QUETION_ID));

        Answer answer = answerRepository.findById(answerNo)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.NO_ANSWER_ID));

        question.selectAnswer(answer);

        // 답변 채택시, 답변 작성자에게 알림 전송
        String linkedUrl = "/questiondetail/" + question.getId();

        Alarm alarm = Alarm.builder()
                .title(answer.getUser().getNickname() + " 님의 댓글이 채택되었습니다.")
                .detail(answer.getDetail())
                .isRead(false)
                .linkedUrl(linkedUrl)
                .registTime(LocalDateTime.now())
                .user(answer.getUser())
                .build();

        answerRepository.save(answer);
        alarmRepository.save(alarm);
    }

}
