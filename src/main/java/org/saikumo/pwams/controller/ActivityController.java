package org.saikumo.pwams.controller;

import io.swagger.annotations.ApiOperation;
import org.saikumo.pwams.dto.ApiResult;
import org.saikumo.pwams.entity.Activity;
import org.saikumo.pwams.entity.Role;
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

    @ApiOperation("获得导师名")
    @PostMapping("/getMentorName")
    public String getMentorName(Long id){//活动id
        try{Activity activity = activityRepository.findById(id);
        Long tid = activity.getMentorId();
        User user = userRepository.findById(tid).get();
        return user.getUsername();
        }catch (Exception e){
            return null;
        }
    }

    @ApiOperation("根据导师Id获取活动")
    @PostMapping("/getActivityByMentor")
    public List<Activity> getActivityByMentor(Long MentorId){//导师ID
        try{List<Activity> activity = activityRepository.findByMentorIdAndStatus(MentorId,"通过");
            return activity;
        }catch (Exception e){
            return null;
        }
    }

    @ApiOperation("根据导师username获取活动")
    @PostMapping("/getActivityByMentorname")
    public List<Activity> getActivityByMentorname(String Mentorname){//导师名
        try{
            User mentor = userRepository.findUserByUsername(Mentorname);
            Long MentorId = mentor.getId();
            List<Activity> activity = activityRepository.findByMentorIdAndStatus(MentorId,"通过");
            return activity;
        }catch (Exception e){
            return null;
        }
    }

    @ApiOperation("报名")
    @PostMapping("/enroll")
    public ApiResult enroll(Long id,String username){
        try {
            Activity act = activityRepository.findById(id);
            User user = userRepository.findUserByUsername(username);
            if(user==null) return ApiResult.fail("未找到该用户");
            List<User> u = act.getUsers();
            for(int i=0;i<u.size();i++){
                if(u.get(i).getUsername().equals(username)){
                    return ApiResult.fail("已报名");
                }
            }
            u.add(user);
            Activity result = activityRepository.save(act);
            if (result != null) {
                return ApiResult.ok("报名成功");
            } else {
                return ApiResult.fail("失败");
            }
        }catch (Exception e){
            return ApiResult.fail("400");
        }
    }

    @ApiOperation("获取导师名单")
    @PostMapping("/getMentor")
    public List<User> getMentor(){
        try {
            Long l = new Long(2);
            Role m = new Role(l,"Mentor");
            List<User> user = userRepository.findByRoles(m);
            return user;
        }catch (Exception e){
            return null;
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
    public ApiResult createAct(String desc,Long menetorId ,String name) {
        try {
            Activity activity = new Activity();
            activity.setDescription(desc);
            activity.setName(name);
            activity.setMentorId(menetorId);
            activity.setStatus("未审核");
            Activity result = activityRepository.save(activity);
            if (result != null) {
                return ApiResult.ok();
            } else {
                return ApiResult.fail("失败");
            }
        }catch (Exception e){
            return ApiResult.fail("400");
        }
    }

    @ApiOperation("工作人员活动审核")
    @PostMapping("/staffCheck")
    public List<Activity> staffCheck() {
        return activityRepository.findByStatus("未审核");
    }

    @ApiOperation("工作人员活动通过审核")
    @PostMapping("/staffPass")
    public ApiResult staffPass(Long id) {
        try {
            Activity activity = activityRepository.findById(id);
            activity.setStatus("通过工作人员审核");
            Activity result = activityRepository.save(activity);
            if (result != null) {
                return ApiResult.ok();
            } else {
                return ApiResult.fail("失败");
            }
        }catch (Exception e){
            return ApiResult.fail("400");
        }
    }

    @ApiOperation("经理活动审核")
    @PostMapping("/managerCheck")
    public List<Activity> managerCheck() {
        return activityRepository.findByStatus("通过工作人员审核");
    }

    @ApiOperation("经理活动通过审核")
    @PostMapping("/managerPass")
    public ApiResult managerPass(Long id) {
        try {
            Activity activity = activityRepository.findById(id);
            activity.setStatus("通过");
            Activity result = activityRepository.save(activity);
            if (result != null) {
                return ApiResult.ok();
            } else {
                return ApiResult.fail("失败");
            }
        }catch (Exception e){
            return ApiResult.fail("400");
        }
    }
}
