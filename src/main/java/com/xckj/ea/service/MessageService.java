package com.xckj.ea.service;

import com.xckj.ea.dao.UserMessage;
import com.xckj.ea.repository.MessageRepository;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    TaskService taskService;
    @Autowired
    MessageRepository messageRepository;

   public boolean  generateMessage(){
        boolean flag=false;

        System.out.println("启动定时任务...");
        List<Task> taskList=taskService.createTaskQuery().list();
         UserMessage  userMessage;
        if(!taskList.isEmpty()) {
            for (Task task : taskList) {
                userMessage=messageRepository.findUserMessageById(task.getId());
                if(userMessage!=null)
                {
                    String  taskId= task.getId();
                    String mesTaskId=userMessage.getTaskId();

                    if( taskId.equals(mesTaskId))
                    {
                        //System.out.println("消息已存在");
                        continue;
                    }
                    else {
                        UserMessage userMessage1=new UserMessage();
                        userMessage1.setTheme(task.getName());
                        userMessage1.setReceiveTime(task.getCreateTime());
                        userMessage1.setUserId(task.getAssignee());
                        userMessage1.setContent(task.getDescription());
                        userMessage1.setTaskId(task.getId());
                        messageRepository.save(userMessage1);
                    }

                }
                else {
                    UserMessage userMessage1=new UserMessage();
                    userMessage1.setTheme(task.getName());
                    userMessage1.setReceiveTime(task.getCreateTime());
                    userMessage1.setUserId(task.getAssignee());
                    userMessage1.setContent(task.getDescription());
                    userMessage1.setTaskId(task.getId());
                    messageRepository.save(userMessage1);

                }

            }
        }





        return flag;
    }

}
