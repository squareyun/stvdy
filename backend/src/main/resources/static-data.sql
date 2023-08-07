INSERT INTO user_state_ns (name) VALUES ('사용자'), ('정지'), ('탈퇴');
INSERT INTO interest_tag_ns (name) VALUES ('자격증'), ('취준'), ('수능');

INSERT INTO participants_role_ns (name) VALUES ('호스트'), ('스태프'), ('참여자');

INSERT INTO authority (authority_name) VALUES ('ROLE_USER');
INSERT INTO authority (authority_name) VALUES ('ROLE_ADMIN');

INSERT INTO user (email, name, password, nickname, activated, state, regist_time) VALUES ('admin@email.com', 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1, '사용자', now());
INSERT INTO user (email, name, password, nickname, activated, state, regist_time) VALUES ('user@email.com', 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1, '사용자', now());

INSERT INTO interest (user_id, tag) VALUES (2, '자격증'), (2, '수능');

INSERT INTO user_authority (user_no, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO user_authority (user_no, authority_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_authority (user_no, authority_name) VALUES (2, 'ROLE_USER');

ALTER TABLE LIKES DROP FOREIGN KEY FK3p9ex2qw452lgox5sboky1e98;
ALTER TABLE LIKES DROP FOREIGN KEY FKnbhujedlj8a26j6mj51fkelve;
ALTER TABLE LIKES DROP INDEX `UK_km61ii1hqf3jniq96ukax3839`, DROP INDEX `UK_4ro7n9gk362k031ltbevy40oi`;