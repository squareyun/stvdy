package com.ssafy.ssap.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_state_ns")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserStateNs {

	@Id
	@Column(length = 10)
	private String name;
}
