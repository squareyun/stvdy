package com.ssafy.ssap.domain.studyroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "participants_role_ns")
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRoleNs {

	@Id
	@Column(length = 10)
	private String name;
}
