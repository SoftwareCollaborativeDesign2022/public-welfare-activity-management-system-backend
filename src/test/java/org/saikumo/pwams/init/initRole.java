package org.saikumo.pwams.init;

import org.junit.jupiter.api.Test;
import org.saikumo.pwams.constant.RoleName;
import org.saikumo.pwams.entity.Role;
import org.saikumo.pwams.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class initRole {
	@Autowired
	RoleRepository roleRepository;

	@Test
	public void initRole(){
		List<Role> roleList = new ArrayList<>();
		for(int i = 0;i < 5;i++){
			roleList.add(new Role());
		}
		roleList.get(0).setName(RoleName.STUDENT.getRoleName());
		roleList.get(0).setId(RoleName.STUDENT.getId());
		roleList.get(1).setName(RoleName.MENTOR.getRoleName());
		roleList.get(1).setId(RoleName.MENTOR.getId());
		roleList.get(2).setName(RoleName.ACTIVITY_ORGANIZER.getRoleName());
		roleList.get(2).setId(RoleName.ACTIVITY_ORGANIZER.getId());
		roleList.get(3).setName(RoleName.STAFF_MEMBER.getRoleName());
		roleList.get(3).setId(RoleName.STAFF_MEMBER.getId());
		roleList.get(4).setName(RoleName.MANAGER.getRoleName());
		roleList.get(4).setId(RoleName.MANAGER.getId());

		roleRepository.saveAllAndFlush(roleList);
	}

}
