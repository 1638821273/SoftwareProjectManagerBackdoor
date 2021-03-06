package cn.edu.xjtu.stu.orangesoft.backdoor.controller;

import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.Objects;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.Operation;
import cn.edu.xjtu.stu.orangesoft.backdoor.pojo.ResultInfo;
import cn.edu.xjtu.stu.orangesoft.backdoor.service.RBACService;
import cn.edu.xjtu.stu.orangesoft.backdoor.service.TaskService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    RBACService rbacService;
    @Autowired
    Gson gson;

    /**
     * 向数据库中添加一个任务
     *
     * @param userID          账号
     * @param userPassword    密码
     * @param taskName        Task名
     * @param taskFinishType  Task初始完成情况
     * @param taskDeadline    Task截止时间
     * @param taskStartTime   Task开始时间
     * @param taskHandlerID   Task执行者
     * @param taskPublisherID Task发布者
     * @param taskDescription Task解释
     * @return ResultInfo: {
     * "resultInfo": String
     * }
     */
    @PostMapping(value = "/task", produces = "application/json;charset=UTF-8")
    public String AddTask(@RequestParam(name = "UserID") Integer userID,
                          @RequestParam(name = "UserPassword") String userPassword,
                          @RequestParam(name = "TaskName") String taskName,
                          @RequestParam(name = "TaskFinishType") Integer taskFinishType,
                          @RequestParam(name = "TaskDeadline") String taskDeadline,
                          @RequestParam(name = "TaskStartTime") String taskStartTime,
                          @RequestParam(name = "TaskHandlerID") Integer taskHandlerID,
                          @RequestParam(name = "TaskPublisherID") Integer taskPublisherID,
                          @RequestParam(name = "TaskDescription") String taskDescription) {
        Objects objects = new Objects();
        objects.setObjectName("task");
        Operation operation = new Operation();
        operation.setOperationDescription("POST");
        if (rbacService.CheckPermission(userID, userPassword, objects, operation)) {
            return gson.toJson(taskService.AddTask(
                    userID,
                    taskName,
                    taskFinishType, taskDeadline,
                    taskStartTime, taskHandlerID,
                    taskPublisherID, taskDescription
            ));
        } else {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setResultInfo("无权访问！！");
            return gson.toJson(resultInfo);
        }
    }

    /**
     * 通过用户名、密码来获取任务
     *
     * @param UserID       用户名
     * @param UserPassword 密码
     * @return if(无权访问) return ResultInfo: {
     * "resultInfo": String
     * } else if(修改成功) return FindTaskResult: {
     * "Finish": String,
     * "Tasks": List[Tasks]
     * }
     */
    @GetMapping(value = "/task", produces = "application/json;charset=UTF-8")
    public String GetTaskByUserID(@RequestParam(name = "UserID") Integer UserID,
                                  @RequestParam(name = "UserPassword") String UserPassword) {
        Objects objects = new Objects();
        objects.setObjectName("task");
        Operation operation = new Operation();
        operation.setOperationDescription("GET");
        if (rbacService.CheckPermission(UserID, UserPassword, objects, operation)) {
            return gson.toJson(taskService.GetTaskByUserID(UserID));
        } else {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setResultInfo("无权访问！！");
            return gson.toJson(resultInfo);
        }
    }

    /**
     * 删除一个任务
     *
     * @param UserID       用户名
     * @param UserPassword 密码
     * @param taskID       被删除的任务的ID
     * @return ResultType: {
     * "resultInfo": String
     * }
     */
    @DeleteMapping(value = "/task", produces = "application/json;charset=UTF-8")
    public String DeleteTask(@RequestParam(name = "UserID") Integer UserID,
                             @RequestParam(name = "UserPassword") String UserPassword,
                             @RequestParam("TaskID") Integer taskID) {
        Objects objects = new Objects();
        objects.setObjectName("task");
        Operation operation = new Operation();
        operation.setOperationDescription("DELETE");
        if (rbacService.CheckPermission(UserID, UserPassword, objects, operation)) {
            return gson.toJson(taskService.DeleteTask(taskID));
        } else {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setResultInfo("无权访问！！");
            return gson.toJson(resultInfo);
        }
    }

    /**
     * 修改一个Task
     *
     * @param userID          用户名
     * @param userPassword    用户密码
     * @param taskID          被修改的Task的ID
     * @param taskName        被修改后的Task的Name
     * @param taskFinishType  被修改后的Task的FinishType
     * @param taskDeadline    被修改后的Task的DeadLine
     * @param taskStartTime   被修改后的Task的StartTime
     * @param taskHandlerID   被修改后的Task的HandlerID
     * @param taskPublisherID 被修改后的PublisherID
     * @param taskDescription 被修改后的Task的Description
     * @param teamID          被修改后的Task的teamID
     * @return ResultInfo: {
     * "resultInfo": String
     * }
     */
    @PutMapping(value = "/task", produces = "application/json;charset=UTF-8")
    public String UpdateTask(@RequestParam(name = "UserID") Integer userID,
                             @RequestParam(name = "UserPassword") String userPassword,
                             @RequestParam(name = "TaskID") Integer taskID,
                             @RequestParam(name = "TaskName") String taskName,
                             @RequestParam(name = "TaskFinishType") Integer taskFinishType,
                             @RequestParam(name = "TaskDeadline") String taskDeadline,
                             @RequestParam(name = "TaskStartTime") String taskStartTime,
                             @RequestParam(name = "TaskHandlerID") Integer taskHandlerID,
                             @RequestParam(name = "TaskPublisherID") Integer taskPublisherID,
                             @RequestParam(name = "TaskDescription") String taskDescription,
                             @RequestParam(name = "TeamID") Integer teamID) {
        Objects objects = new Objects();
        objects.setObjectName("task");
        Operation operation = new Operation();
        operation.setOperationDescription("PUT");
        if (rbacService.CheckPermission(userID, userPassword, objects, operation)) {
            return gson.toJson(taskService.TaskUpdate(
                    taskID, taskName,
                    taskFinishType, taskDeadline,
                    taskStartTime, taskHandlerID,
                    taskPublisherID, taskDescription
            ));
        } else {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setResultInfo("无权访问！！");
            return gson.toJson(resultInfo);
        }
    }
}
