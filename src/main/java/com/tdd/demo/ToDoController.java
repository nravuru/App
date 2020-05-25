/**
 * 
 */
package com.tdd.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdd.demo.entity.ToDo;
import com.tdd.demo.svc.ToDoService;

/**
 * @author naresh.ravurumckesson.com
 *
 */
@RestController
@RequestMapping("/")
public class ToDoController {

	@Autowired
	private ToDoService toDoService;
	
	@GetMapping("/todos")
	public List<ToDo> getAllToDos() {
		return toDoService.getAllToDos();
	}
	
	@GetMapping("/todos/{username}")
	public List<ToDo> getAllToDosByUsername(@PathVariable("username") String username) {
		return toDoService.getAllToDosByUserName(username);
	}
	
	@PostMapping("/todos/add")
	public ToDo addToDo(ToDo todo) {
		return toDoService.addToDo(todo);
	}
	
	@PutMapping("/todos/update")
	public ToDo updateToDo(ToDo todo) {
		return toDoService.updateToDo(todo);
	}
	
	@DeleteMapping("/todos/delete/{id}")
	public String updateToDo(@PathVariable("id") Long id) {
		Long idResp = toDoService.deleteToDoById(id);
		if (idResp != null) {
			return "Record deleted";
		} else {
			return "";
		}
	}
	
	@GetMapping("/todos/{userName}/{completeBln}")
	public List<ToDo> getAllCompletedTasks(@PathVariable("userName") String userName,
			@PathVariable("completeBln") String completeBln) {
		List<ToDo> tasks = null;
		
		if (Boolean.parseBoolean(completeBln)) {
			tasks = toDoService.getAllCompletedTasks(userName);
		} else {
			tasks = toDoService.getAllOutstandingTasks(userName);
		}
		
		return tasks; 
	}
}
