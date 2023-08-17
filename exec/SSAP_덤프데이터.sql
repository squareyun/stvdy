-- user 1~10번: 관리자 (비번 1234)
INSERT INTO user (email, password, name, nickname, activated, state, regist_time) VALUES ('totalAlarm@stvdy.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'totalAlarm', '전체공지용계정', 1, '사용자', now()),
('advertise@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', 'advertisement', '광고용계정', 1, '사용자', now()),
('captinkim@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김범기', 'captinkim', 1, '사용자', now()),
('soy@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김소이', 'so2e', 1, '사용자', now()),
('monet@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '윤우혁', 'monet', 1, '사용자', now()),
('andrew@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '안대현', 'andrew', 1, '사용자', now()),
('roscoe@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '안세혁', 'roscoe', 1, '사용자', now()),
('deegun@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '이대근', 'deegun', 1, '사용자', now()),
('kimssaps@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김쌉스', 'ssaps', 1, '사용자', now()),
('parktvdy@stvdy.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '박터디', 'tvdy', 1, '사용자', now());

INSERT INTO user_authority (user_no, authority_name) VALUES (1, 'ROLE_USER'), (1, 'ROLE_ADMIN'), (2, 'ROLE_USER'), (2, 'ROLE_ADMIN'),
(3, 'ROLE_USER'), (3, 'ROLE_ADMIN'), (4, 'ROLE_USER'), (4, 'ROLE_ADMIN'), (5, 'ROLE_USER'), (5, 'ROLE_ADMIN'), (6, 'ROLE_USER'), (6, 'ROLE_ADMIN'),
(7, 'ROLE_USER'), (7, 'ROLE_ADMIN'), (8, 'ROLE_USER'), (8, 'ROLE_ADMIN'), (9, 'ROLE_USER'), (9, 'ROLE_ADMIN'), (10, 'ROLE_USER'), (10, 'ROLE_ADMIN');

-- user 11~50번: 일반 사용자 (비번 1234)
INSERT INTO user (email, password, name, nickname, activated, state, regist_time) VALUES ('user11@example.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '전현무', '무무랜드', 1, '사용자', NOW()),
('user12@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '박서준', '박서준입니다', 1, '사용자', NOW()),
('user13@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '강정훈', '닭강정hoon', 1, '사용자', NOW()),
('user14@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '공민정', 'zero민', 1, '사용자', NOW()),
('user15@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '곽세미', '쾈마', 1, '사용자', NOW()),
('user16@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김소현', '언제나맑음', 1, '사용자', NOW()),
('user17@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '권지원', '먹깨비77', 1, '사용자', NOW()),
('user18@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김이영', '202020', 1, '사용자', NOW()),
('user19@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김건우', 'wooooooo', 1, '사용자', NOW()),
('user20@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '최고은', 'best', 1, '사용자', NOW()),
('user21@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김다솜', 'cotton', 1, '사용자', NOW()),
('user22@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김다운', 'updown', 1, '사용자', NOW()),
('user23@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김도윤', '코딩이란', 1, '사용자', NOW()),
('user24@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김민영', '미녕이라', 1, '사용자', NOW()),
('user25@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '김보라', 'purple', 1, '사용자', NOW()),
('user26@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '류승민', 'winmin', 1, '사용자', NOW()),
('user27@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '문양희', '히히히히', 1, '사용자', NOW()),
('user28@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '민정은', '실버은정', 1, '사용자', NOW()),
('user29@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '박정원', '가든팍팍', 1, '사용자', NOW()),
('user30@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '박선주', '미나리노', 1, '사용자', NOW()),
('user31@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '문상훈', '빠더너스', 1, '사용자', NOW()),
('user32@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '박시형', '세무사뿌셔', 1, '사용자', NOW()),
('user33@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '방태화', '뱅뱅뱅bang', 1, '사용자', NOW()),
('user34@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '배재성', '배재성취뽀', 1, '사용자', NOW()),
('user35@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '백승재', 'win100', 1, '사용자', NOW()),
('user36@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '변규미', '변규미22', 1, '사용자', NOW()),
('user37@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '안상희', '코테뿌셔', 1, '사용자', NOW()),
('user38@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '안세영', '세영안세영', 1, '사용자', NOW()),
('user39@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '박시은', '박시은11', 1, '사용자', NOW()),
('user40@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '신동엽', '신동엽임', 1, '사용자', NOW()),
('user41@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '심혜수', '취뽀하자', 1, '사용자', NOW()),
('user42@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '이현지', '잠은죽어서', 1, '사용자', NOW()),
('user43@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '이하영', '노는게제일조아', 1, '사용자', NOW()),
('user44@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '임지윤', 'thumbsup', 1, '사용자', NOW()),
('user45@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '장우인', '전교회장', 1, '사용자', NOW()),
('user46@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '주재영', '영아영아', 1, '사용자', NOW()),
('user47@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '하이송', 'hihello', 1, '사용자', NOW()),
('user48@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '지상호', '지상호우', 1, '사용자', NOW()),
('user49@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '한민지', 'minzi', 1, '사용자', NOW()),
('user50@email.com', '$2a$10$4fg8iHpYK83dljHxXCtgDe0iHtFug8D3P4qHgYYARe.uFPnq2uDqy', '최승호', 'winho', 1, '사용자', NOW());

INSERT INTO user_authority (user_no, authority_name) VALUES (11, 'ROLE_USER'), (12, 'ROLE_USER'), (13, 'ROLE_USER'), (14, 'ROLE_USER'),
(15, 'ROLE_USER'), (16, 'ROLE_USER'), (17, 'ROLE_USER'), (18, 'ROLE_USER'), (19, 'ROLE_USER'), (20, 'ROLE_USER'), (21, 'ROLE_USER'), (22, 'ROLE_USER'),
(23, 'ROLE_USER'), (24, 'ROLE_USER'), (25, 'ROLE_USER'), (26, 'ROLE_USER'), (27, 'ROLE_USER'), (28, 'ROLE_USER'), (29, 'ROLE_USER'), (30, 'ROLE_USER'),
(31, 'ROLE_USER'), (32, 'ROLE_USER'), (33, 'ROLE_USER'), (34, 'ROLE_USER'), (35, 'ROLE_USER'), (36, 'ROLE_USER'), (37, 'ROLE_USER'), (38, 'ROLE_USER'),
(39, 'ROLE_USER'), (40, 'ROLE_USER'), (41, 'ROLE_USER'), (42, 'ROLE_USER'), (43, 'ROLE_USER'), (44, 'ROLE_USER'), (45, 'ROLE_USER'), (46, 'ROLE_USER'),
(47, 'ROLE_USER'), (48, 'ROLE_USER'), (49, 'ROLE_USER'), (50, 'ROLE_USER');

INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (73, 39, '2023-07-05 12:30:51', '수능 공부 팁', '수능', '수능을 위한 공부 팁이 궁금합니다. 꾸준히 공부하는 것 말고도..\n\n공부에 대한 동기를 어떻게 유지할 수 있을까요?\n다른 여러가지 공부법도 궁금합니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (42, 24, '2023-08-09 23:10:45', 'Python 기초 질문', 'Python', 'Python에서 리스트와 튜플의 차이점이 무엇인가요? \n\n그리고 상황에 따라 어떤 것을 사용하면 좋을지 모르겠어요. \n어떤 경우에는 리스트를 사용하는 게 좋고, 어떤 경우엔 튜플이 좋을까요?\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (21, 15, '2023-08-01 18:32:02', 'JAVA 객체지향 설명', 'JAVA', '자바에서 객체지향 프로그래밍의 개념과 장점, 단점에 대해 설명 부탁드려요.\n\n객체지향인 이유가 무엇인가요?\n또한, 객체지향 프로그래밍이 주는 이점은 무엇인가요?\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (50, 33, '2023-07-20 16:25:47', '임용고시 준비', '임용고시', '임용고시를 준비하려면 어떤 계획을 세워야 할까요?\n\n수업을 듣거나 참고할만한 자료 추천 좀 해주세요.\또한, 시험 중점 사항도 꼭 알려주시길 바랍니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (9, 48, '2023-08-15 09:30:37', 'SQL 조인 질문', 'DB', 'SQL에서 INNER JOIN과 OUTER JOIN의 차이에 대해 설명해주실 수 있나요?\n\nOUTER JOIN은 어떤 종류가 있나요?\n또한, 실무에서 어느 상황에서 어떤 JOIN을 사용하는지 궁금합니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (89, 27, '2023-08-15 20:05:52', '웹 개발자 스킬', '개발자', '웹 개발자가 되기 위해 어떤 스킬을 학습해야 할까요?\n\n프론트엔드와 백엔드의 차이점도 궁금합니다.\n웹 개발자의 역할과 진로에 대해서도 알려주세요.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (67, 41, '2023-08-03 14:54:26', '코딩 면접 준비 방법', '코딩', '코딩 면접을 준비하려면 어떤 방식으로 연습해야 할까요?\n\n현장에서 많이 물어보는 질문들도 궁금해요.\n그리고 실력 향상을 위한 스터디 추천도 부탁드립니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (35, 12, '2023-08-04 21:40:35', '자료구조와 알고리즘 질문', '개발', '자료구조와 알고리즘이 왜 중요한지요?다양한 역해서 가지는 이유는 무엇인가요? \n\n구체적으로 어떤 역할을 하는지도 알려주세요.\n또한, 코딩 인터뷰에서 자주 보는 자료구조와 알고리즘 유형은 무엇인가요?\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (60, 20, '2023-08-13 12:28:17', 'MySQL과 MariaDB', 'DB', 'MySQL과 MariaDB의 차이점이 궁금해요\n\n둘 중 어떤 것을 사용하는 것이 좋은가요?\nMariaDB로 전환할 때 주의해야할 점은 무엇인지도 알려주세요.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (78, 25, '2023-08-11 17:15:33', 'Python 웹 프레임워크', 'Python', 'Python 웹 프레임워크 중 Flask와 Django의 장단점을 알려주세요.\n\n웹 개발을 처음 하는 사람들은 어떤 프레임워크를 선택하는 것이 좋을까요?\n그 두 프레임워크에 대한 예제 코드나 참고자료도 소개해주세요.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (4, 50, '2023-08-06 13:22:14', '수능 영어 준비 방법', '수능', '수능 영어 공부를 시작하려고 하는데 어떤 방식으로 준비해야 할까요?\n\n파닉 영숙이나 모의고사를 많이 풀어보는 것 외에 다른 방법도 호기심이 생깁니다.\n자주 나오는 문제 유형이나 효과적인 기출 문제 풀이도 도움부탁드릴게요.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (97, 19, '2023-08-02 10:47:21', '자바 가비지 컬렉션', 'JAVA', '자바 가비지 컬렉션(Garbage Collection)에 대한 설명을 부탁드립니다.\n\n가비지 컬렉션의 원리와 필요성, 그리고 가비지 컬렉션과 관련된 최적화에 대해 알고 싶습니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (55, 46, '2023-08-11 03:24:05', 'API 개발 가이드라인', '개발자', '웹 API 개발자가 되려면 어떤 가이드라인들을 따라야 하나요?\n\nRESTful API와 같은 개발 방식이 있다고 들었는데, 이것 외에도 알아야할 것들이 궁금합니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (16, 23, '2023-08-08 19:40:31', 'MYSQL 테이블 설계', 'DB', 'MYSQL 테이블 설계시 고려해야 할 사항들은 무엇인가요?\n\n정규화와 같은 내용 외에도 성능 향상에 도움이 되는 테이블 설계 원칙도 궁금합니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (30, 35, '2023-08-11 22:16:49', '알고리즘 문제 추천', '코딩', '알고리즘 문제를 풀고 싶은데, 추천할만한 문제와 사이트를 알려주세요.\n\n코딩 테스트 대비를 위한 문제 풀이 사이트들도 궁금합니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (28, 40, '2023-08-12 15:03:14', 'Python 라이브러리', 'Python', 'Python에서 유용한 라이브러리들이 뭐가 있을까요?\n\n데이터 분석, 웹 개발, 기계 학습 등 다양한 분야에 적용할 수 있는 라이브러리들을 알려주세요.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (82, 16, '2023-08-01 20:50:32', '자바 스프링 프레임워크', 'JAVA', '자바 스프링 프레임워크에 대해 알려주세요.\n\n스프링의 특징과 기능 및 스프링 프로젝트를 시작하는 방법도 함께 설명해주시길 바랍니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (29, 11, '2023-08-10 04:33:58', '코딩 테스트 공부법', '코딩', '코딩 테스트를 준비하려면 어떻게 공부해야 하나요?\n\n어떤 사이트나 교재교재를 이용하여 공부를 시작하는 것이 좋을까요?\n도움이 될만한 질문들이나 기출 문제들도 추천해 주세요.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (71, 44, '2023-08-16 07:06:39', '자바스크립트 튜토리얼 추천', '개발자', '자바스크립트를 처음 배우려고 하는데, 추천해주실만한 튜토리얼 사이트나 강좌를 알려주세요.\n\n온라인에서 무료로 배울 수 있는 자료가 좋지만, 유료 강좌도 괜찮습니다.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (63, 29, '2023-08-01 11:15:20', '데이터베이스 튜닝', 'DB', '데이터베이스 튜닝에 대해 알려주세요.\n\n성능을 향상시키기 위해 어떤 수정사항들을 고려해야 할까요?\n데이터베이스 튜닝의 중요성과 관련 프로세스에 대해서도 설명해주세요.\n');
INSERT INTO question (hit, user_id, regist_time, title, category, detail) VALUES (96, 17, '2023-08-14 15:47:06', '임용고시 영어 공부법', '임용고시', '임용고시 영어 공부를 시작하려고 하는데 어떤 방식으로 준비해야 할까요?\n');

INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (1, 25, '2023-08-16 14:00:00', '수능 공부 팁은 개인의 성향과 목표에 따라 다양합니다. 공부에 대한 동기를 유지하기 위해 목표를 세우고, 주기적으로 성취감을 느낄 수 있는 단기간 목표를 설정하세요. 또한 동료들과 스터디 그룹을 만들어 서로 격려하며 공부하는 것도 도움이 됩니다. 공부법에는 셀프스트런트 메소드, SQ3R 등 다양한 방법들이 있으니 선호하는 방법을 찾아보세요.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (2, 34, '2023-08-15 22:00:00', 'Python에서 리스트는 가변적이고, 튜플은 불변입니다. 즉, 리스트는 변경 가능하지만, 튜플은 생성된 후 변경할 수 없습니다. 리스트는 일반적으로 동일한 형태의 데이터를 저장할 때 사용하며, 튜플은 변경되지 않아야 하는 구조체와 같은 데이터를 저장할 때 사용합니다. 사용 상황에 따라 적절한 자료형을 선택하는 것이 중요합니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (3, 13, '2023-08-02 16:30:00', '자바에서 객체지향 프로그래밍은 현실 세계의 사물과 개념을 객체라는 단위로 모델링하여 코드를 작성하는 개념입니다. 이러한 방식은 코드 재사용성과 확장성을 향상시킬 수 있습니다. 객체지향 프로그래밍의 주된 장점으로는 코드의 가독성, 중복 방지, 코드 유지 보수의 용이성 등이 있습니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (4, 28, '2023-07-22 18:35:00', '임용고시 준비를 위해 첫째로 시험 범위를 파악한 다음, 계획표를 작성해보세요. 시험과 관련된 강의나 교재를 구입하여 학습 자료를 확보하고, 모의고사를 반복적으로 풀어 시험 대비를 합니다. 선생님들의 강의 동영상이나 공인 온라인 강의를 참고하세요. 시험 중점 사항은 그 시기의 최신 교육 정책 및 시험 공고를 참고하세요.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (5, 16, '2023-08-15 15:35:00', 'SQL에서 INNER JOIN은 두 테이블 간의 교집합 결과를 반환합니다. OUTER JOIN은 두 테이블 간에 일치하는 항목이 없어도 결과를 반환합니다. OUTER JOIN에는 LEFT, RIGHT 및 FULL OUTER JOIN이 있습니다. 실무에서는 주로 공통 열을 기준으로 두 테이블의 데이터를 조합할 때 INNER JOIN을 사용하고, 누락된 데이터를 찾거나 관계를 포괄적으로 파악할 때 OUTER JOIN을 사용합니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (6, 14, '2023-07-27 20:55:00', '웹 개발자가 되기 위해 프론트엔드와 백엔드 기술을 모두 공부할 필요가 있습니다. 프론트엔드는 사용자 인터페이스를 관리하며 HTML, CSS, JavaScript 등의 기술을 사용합니다. 백엔드는 서버와 데이터베이스를 관리하고, 서버 사이드 언어를 사용합니다. 웹 개발자가 처리하는 주요 업무로는 웹 사이트나 웹 애플리케이션을 설계하고 구현하는 것입니다. 진로로는 개발자, 프로젝트 관리자, 기술 분석가 등이 있습니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (7, 42, '2023-08-05 17:35:00', '코딩 면접을 준비하려면 먼저 알고리즘 문제를 많이 풀어봐야 합니다. 다양한 알고리즘 사이트에서 문제를 찾을 수 있습니다. 예를 들어 백준, 프로그래머스, 릿코드 등의 사이트가 있습니다. 스터디를 통해 함께 공부하고 서로 피드백을 주고받는 것도 도움이 됩니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (8, 43, '2023-08-06 22:15:00', '자료구조와 알고리즘은 프로그래밍에 있어서 기본적인 지식으로, 문제 해결 능력을 향상시키는데 큰 도움이 됩니다. 데이터를 저장, 처리하는 데 필요한 자료구조를 사용하고, 효율적인 문제 해결 방식을 찾는 알고리즘에 대한 이해가 필수입니다. 코딩 인터뷰에서 자주 보이는 자료구조에는 배열, 연결 리스트 등이 있으며, 알고리즘 유형에는 정렬, 이진 탐색 등이 있습니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (9, 40, '2023-08-15 13:30:00', 'MySQL은 상용 데이터베이스 관리 시스템으로 널리 쓰이며, MariaDB는 MySQL의 오픈 소스 포크로 개발되어 있습니다. 둘의 기능과 성능은 매우 비슷하며, 여러 가지 차이점이 있지만 대부분의 경우 어느 것을 사용해도 큰 차이가 없습니다. MariaDB로 전환할 때 주의할 점에는 기존 MySQL과 호환되지 않는 기능이나 구조 변경 등이 있으니 전환 전에 호환성을 확인하시는 것이 좋습니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (10, 19, '2023-08-12 11:16:00', 'Python 웹 프레임워크인 Flask는 가볍고 확장 가능한 특성을 가지고 있어 애플리케이션의 기본 기능을 빠르게 구현할 수 있습니다. 반면, Django는 복잡한 웹 애플리케이션 개발에 더 적합하며, 다양한 기능이 이미 포함되어 있어 개발 속도를 향상시킬 수 있습니다. 입문자는 처음에는 Flask를 사용해 보고, 나중에 프로젝트의 요구 사항에 따라 적합한 프레임워크를 선택하는 것이 좋습니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (11, 19, '2023-08-13 19:45:00', '수능 영어 공부에는 독해와 작문을 연습하는 것이 중요합니다. 책을 읽거나 영화를 시청하는 등 다양한 방식으로 영어에 노출되는 것이 좋습니다. 또한, 친구들과 영어로 소통하는 습관을 들이는 것도 도움이 됩니다. 모의고사를 많이 풀어봄으로써 정답률을 높일 수 있고, 파닉영숙 전략을 연구해 보세요.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (12, 41, '2023-08-16 08:30:00', '자바 가비지 컬렉션은 자동 메모리 관리 시스템의 일부로 사용되지 않는 메모리를 회수하는 과정입니다. 가비지 컬렉션을 통해 프로그래머는 메모리 누수를 줄이고, 시스템의 안정성을 향상시킬 수 있습니다. 가비지 컬렉션과 관련된 최적화에는 객체 생성 최소화, 메모리 할당 효율성 향상 등이 있습니다.');
INSERT INTO answer (question_id, user_id, regist_time, detail) VALUES (13, 21, '2023-08-12 14:30:00', '웹 API 개발자로서 가이드라인에 따라 개발하려면 RESTful API 이외에도 다음과 같은 사항들을 고려해야 합니다:\n\n1. 적절한 HTTP 메서드 사용: GET, POST, PUT, DELETE 등의 HTTP 메서드를 사용하여 리소스의 CRUD 작업을 명확하게 나타내세요.\n2. 경로 설계: URI를 일관되고 명확한 형태로 작성하세요. 가급적 리소스를 명사로 표현하고, 리소스 간의 관계를 논리적으로 구성하십시오.\n3. 상태 코드 사용: HTTP 상태 코드를 사용하여 클라이언트에게 응답 결과를 명확하게 전달하세요.\n4. 인증 및 권한 관리: 반드시 필요한 경우 API 인증 및 권한 관리를 구현해 사용자의 데이터와 리소스에 대한 보안을 유지하세요.\n5. JSON 또는 XML을 사용한 응답 포맷: API 응답 형식으로 JSON이나 XML 같은, 클라이언트가 쉽게 처리할 수 있는 형식을 사용하세요.\n6. 올바른 에러 처리: 에러 발생 시 명확한 에러 메시지와 상태 코드를 사용하여 클라이언트에게 에러 상황을 알려주세요.\n7. API 버전 관리: API가 변경될 가능성이 있다면, 버전 관리를 통해 이전 버전과의 호환성을 유지하는 데 도움을 받으십시오.\n\n이러한 가이드라인들을 따르면 웹 API 개발에 도움이 될 것입니다.');

INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 5, 22);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 14);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 15);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 16);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 17);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 18);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 19);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 20);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 21);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 6, 22);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 11, 19);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 11, 20);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 11, 21);
INSERT INTO likes (is_good, question_id, user_id) VALUES (1, 11, 22);
INSERT INTO likes (is_good, question_id, user_id) VALUES (0, 16, 23);
INSERT INTO likes (is_good, question_id, user_id) VALUES (0, 19, 24);
INSERT INTO likes (is_good, question_id, user_id) VALUES (0, 19, 25);
INSERT INTO likes (is_good, question_id, user_id) VALUES (0, 19, 26);

UPDATE question SET best_answer = 5 where id = 5;
UPDATE question SET best_answer = 6 where id = 6;
UPDATE question SET best_answer = 11 where id = 11;

# 방 덤프데이터 생성(유저 11~50 사용)
## 입력용프로시저 생성
DELIMITER $
CREATE PROCEDURE `join_room_proc`(
    P_ROLE VARCHAR(10)
	, P_USER_ID INT
    , P_ROOM_ID INT
)
BEGIN
        # participant
        INSERT INTO participant(role, user_id, room_id)
        values(P_ROLE, P_USER_ID, P_ROOM_ID);
        # room_log
        INSERT INTO room_log(enter_time, user_id, room_id)
        values(current_timestamp(), P_USER_ID, P_ROOM_ID);
END $
CREATE PROCEDURE `add_room_proc` (
	P_USER_ID INT
    , P_ROLE VARCHAR(10)
    , P_TITLE VARCHAR(20)
    , P_QUOTA TINYINT
    , P_IS_PRIVACY BIT(1)
    , P_PASSWORD VARCHAR(45)
    , P_ENDTIME TIMESTAMP
    , P_RULE TEXT
)
BEGIN
	DECLARE V_ROOM_ID INT;
    DECLARE V_IMAGE_PATH VARCHAR(100);
    SET V_IMAGE_PATH = (select room_image_path from user where id=P_USER_ID);
    IF P_ENDTIME is null THEN SET P_ENDTIME = DATE_ADD(current_timestamp(),INTERVAL 3 HOUR); END IF;
    START TRANSACTION;
		# "room, participant, room_log 테이블 순서대로 insert";
		INSERT INTO room(title, quota, is_privacy, password, end_time, rule)
        values(P_TITLE, P_QUOTA, P_IS_PRIVACY, P_PASSWORD, P_ENDTIME, P_RULE);
        
        SET V_ROOM_ID = (SELECT id FROM room WHERE P_TITLE = title ORDER BY id DESC LIMIT 1);
        
		CALL join_room_proc(P_ROLE, P_USER_ID, V_ROOM_ID);
    COMMIT;
END $
DELIMITER ;

## 방 20개 생성
CALL add_room_proc(11,'호스트','스터디룸1',16,1,null/*password*/,null/*end_time*/,'참여자끼리 귓속말 금지');
CALL add_room_proc(12,'호스트','스터디룸2',16,1,null/*password*/,null/*end_time*/,'친목질 금지');
CALL add_room_proc(13,'호스트','스터디룸3',16,1,null/*password*/,null/*end_time*/,'딴짓 시 바로 강퇴');
CALL add_room_proc(14,'호스트','스터디룸4',16,1,null/*password*/,null/*end_time*/,'자습방~~~');
CALL add_room_proc(15,'호스트','스터디룸5',16,1,null/*password*/,null/*end_time*/,'자격증 준비합시다');

CALL add_room_proc(16,'호스트','스터디룸6',16,1,null/*password*/,null/*end_time*/,'터치X');
CALL add_room_proc(17,'호스트','스터디룸7',16,1,null/*password*/,null/*end_time*/,'그라운드 룰1');
CALL add_room_proc(18,'호스트','스터디룸8',16,1,null/*password*/,null/*end_time*/,'성공해서 공부합시다1');
CALL add_room_proc(19,'호스트','스터디룸9',16,1,null/*password*/,null/*end_time*/,'성공해서 공부합시다2');
CALL add_room_proc(20,'호스트','스터디룸10',16,1,null/*password*/,null/*end_time*/,'성공해서 공부합시다3');

CALL add_room_proc(21,'호스트','스터디룸11',16,1,null/*password*/,null/*end_time*/,'알아서 공부하다 나가시우');
CALL add_room_proc(22,'호스트','스터디룸12',16,1,null/*password*/,null/*end_time*/,'프라이버시 모드 공부방');
CALL add_room_proc(23,'호스트','스터디룸13',16,1,null/*password*/,null/*end_time*/,'얍얍욥욥');
CALL add_room_proc(24,'호스트','스터디룸14',16,1,null/*password*/,null/*end_time*/,'참여자끼리 귓속말 금지2');
CALL add_room_proc(25,'호스트','스터디룸15',16,1,null/*password*/,null/*end_time*/,'참여자끼리 귓속말 금지3');

CALL add_room_proc(26,'호스트','화상회의룸1',16,0,1234/*password*/,null/*end_time*/,'AA사 화상회의');
CALL add_room_proc(27,'호스트','화상회의룸2',8,0,1111/*password*/,null/*end_time*/,'BB사 화상회의');
CALL add_room_proc(28,'호스트','화상회의룸3',4,0,2222/*password*/,null/*end_time*/,'CC사 화상회의');
CALL add_room_proc(29,'호스트','화상회의룸4',3,0,3333/*password*/,null/*end_time*/,'DD사 화상회의');
CALL add_room_proc(30,'호스트','화상회의룸5',10,0,4444/*password*/,null/*end_time*/,'EE사 화상회의');

## 15번 방에 10명 입장
CALL join_room_proc("참여자", 31, 15); CALL join_room_proc("참여자", 32, 15); CALL join_room_proc("참여자", 33, 15); CALL join_room_proc("참여자", 34, 15); CALL join_room_proc("참여자", 35, 15);
CALL join_room_proc("참여자", 36, 15); CALL join_room_proc("참여자", 37, 15); CALL join_room_proc("참여자", 38, 15); CALL join_room_proc("참여자", 39, 15); CALL join_room_proc("참여자", 40, 15);
## 19번 방에 2명 입장(full)
CALL join_room_proc("참여자", 41, 19); CALL join_room_proc("참여자", 42, 19); 
## 1~8번 방에 1명씩 입장
CALL join_room_proc("참여자", 42, 1); CALL join_room_proc("참여자", 43, 2); CALL join_room_proc("참여자", 44, 3); CALL join_room_proc("참여자", 45, 4); 
CALL join_room_proc("참여자", 46, 5); CALL join_room_proc("참여자", 47, 6); CALL join_room_proc("참여자", 48, 7); CALL join_room_proc("참여자", 49, 8); 
