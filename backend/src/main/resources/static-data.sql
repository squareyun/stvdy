INSERT INTO user_state_ns (name) VALUES ('일반'), ('정지'), ('탈퇴');

INSERT INTO participants_role_ns (name) VALUES ('호스트'), ('스태프'), ('참여자');

INSERT INTO question_category_ns (name) VALUES ('수능'), ('국어'), ('수학'), ('모의고사'), ('6월 모의고사'), ('개발&프로그래밍'), ('DB'), ('JAVA'), ('PYTHON'), ('알고리즘'), ('다익스트라 알고리즘');

INSERT INTO authority_ns (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO user (email, name, password, nickname, activated) VALUES ('admin@email.com', 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);
INSERT INTO user (email, name, password, nickname, activated) VALUES ('user@email.com', 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1);
INSERT INTO user (email, name, password, nickname, activated) VALUES ('E@E', 'test', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'test', 1);

INSERT INTO user_authority (user_no, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO user_authority (user_no, authority_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_authority (user_no, authority_name) VALUES (2, 'ROLE_USER');
