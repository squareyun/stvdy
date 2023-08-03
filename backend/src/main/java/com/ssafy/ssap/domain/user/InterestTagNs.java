package com.ssafy.ssap.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interest_tag_ns")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterestTagNs {

	@Id
	@Column(length = 6)
	private String name;
}
