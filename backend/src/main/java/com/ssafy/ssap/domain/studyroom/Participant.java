package com.ssafy.ssap.domain.studyroom;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("checkstyle:RegexpSinglelineJava")
@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "is_out", columnDefinition = "bit")
	@ColumnDefault("0")
	private Boolean isOut;

	@NotNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "role")
	private ParticipantRoleNs role;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "room_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Room room;
}
