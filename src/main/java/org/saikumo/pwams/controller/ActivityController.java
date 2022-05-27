package org.saikumo.pwams.controller;

import io.swagger.annotations.ApiOperation;
import org.saikumo.pwams.entity.Activity;
import org.saikumo.pwams.entity.User;
import org.saikumo.pwams.repository.ActivityRepository;
import org.saikumo.pwams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;

    @ApiOperation("所有活动")
    @PostMapping("/findAll")
    public List<Activity> findAll(){
        return activityRepository.findAll();
    }

    @ApiOperation("用户查看所有活动")
    @PostMapping("/userFind")
    public List<Activity> userFind(){
        return activityRepository.findByStatus("通过");
    }

    @ApiOperation("活动描述")
    @PostMapping("/getDesc")
    public Activity getDesc(Long id){
        return activityRepository.findById(id);
    }

    @ApiOperation("报名")
    @PostMapping("/enroll")
    public String enroll(Long id,Long uid){
        try {
            Activity act = activityRepository.findById(id);
            User user = userRepository.findById(uid).get();
            List<User> u = act.getUsers();
            for(int i=0;i<u.size();i++){
                if(u.get(i).getId()==uid){
                    return "已报名";
                }
            }
            u.add(user);
            Activity result = activityRepository.save(act);
            if (result != null) {
                return "success";
            } else {
                return "false";
            }
        }catch (Exception e){
            return "false";
        }
    }

    @ApiOperation("查看报名学员")
    @PostMapping("/enrollUser")
    public List<User> enrollUser(Long id){
        try{
            Activity activity = activityRepository.findById(id);
            return activity.getUsers();
        }catch (Exception e){
            return null;
        }

    }

    @ApiOperation("活动申请")
    @PostMapping("/createAct")
    public String createAct(String desc,Long menetorId ,String name) {
        try {
            Activity activity = new Activity();
            activity.setDescription(desc);
            activity.setName(name);
            activity.setMentorId(menetorId);
            activity.setStatus("未审核");
            Activity result = activityRepository.save(activity);
            if (result != null) {
                return "success";
            } else {
                return "false";
            }
        }catch (Exception e){
            return "false";
        }

    }

    @ApiOperation("工作人员活动审核")
    @PostMapping("/staffCheck")
    public List<Activity> staffCheck() {
        return activityRepository.findByStatus("未审核");
    }

    @ApiOperation("工作人员活动通过审核")
    @PostMapping("/staffPass")
    public String staffPass(Long id) {
        try {
            Activity activity = activityRepository.findById(id);
            activity.setStatus("通过工作人员审核");
            Activity result = activityRepository.save(activity);
            if (result != null) {
                return "success";
            } else {
                return "false";
            }
        }catch (Exception e){
            return "false";
        }
    }

    @ApiOperation("经理活动审核")
    @PostMapping("/managerCheck")
    public List<Activity> managerCheck() {
        return activityRepository.findByStatus("通过工作人员审核");
    }

    @ApiOperation("经理活动通过审核")
    @PostMapping("/managerPass")
    public String managerPass(Long id) {
        try {
            Activity activity = activityRepository.findById(id);
            activity.setStatus("通过");
            Activity result = activityRepository.save(activity);
            if (result != null) {
                return "success";
            } else {
                return "false";
            }
        }catch (Exception e){
            return "false";
        }
    }
}
