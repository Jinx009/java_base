package main.entry.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.basicFunctions.business.TaskService;

@Controller
public class TaskController {

	@Autowired 
	private TaskService taskService;
	
	@RequestMapping(value = "/task")
	@ResponseBody
	public String task(){
		try {
			taskService.task();
			return "success";
		} catch (Exception e) {
			System.out.println("error"+e);
		}
		return "fail";
	}
	
}
