package org.saikumo.pwams.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
public enum RoleName {

	STUDENT(1L,"学员"),
	MENTOR(2L,"导师"),
	ACTIVITY_ORGANIZER(3L,"活动组织者"),
	STAFF_MEMBER(4L,"工作人员"),
	MANAGER(5L,"经理");

	private final Long id;
	private final String roleName;
}
