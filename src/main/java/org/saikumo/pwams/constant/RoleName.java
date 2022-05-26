package org.saikumo.pwams.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {

	STUDENT("学员"),
	MENTOR("导师"),
	ACTIVITY_ORGANIZER("活动组织者"),
	STAFF_MEMBER("工作人员"),
	MANAGER("经理");

	private final String roleName;
}
