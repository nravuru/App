/**
 * 
 */
package com.tdd.demo.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdd.demo.entity.ToDo;
import com.tdd.demo.repo.ToDoRepository;

/**
 * @author naresh.ravurumckesson.com
 *
 */
@Service
public class ToDoService {

	@Autowired
	ToDoRepository toDoRepo;
	
	public ToDoService(ToDoRepository toDoRepo) {
		this.toDoRepo = toDoRepo;
	}

	public List<ToDo> getAllToDos() {
		return (List<ToDo>) toDoRepo.findAll();
		
	}
	
	public ToDo getToDoById(Long id) {
		return toDoRepo.findToDoById(id);
	}
	
	public List<ToDo> getAllToDosByUserName(String userName) {
		return toDoRepo.findAllToDosByUserName(userName);
	}

	public ToDo addToDo(ToDo todo) {
		return toDoRepo.save(todo);
	}

	public ToDo updateToDo(ToDo todo) {
		return toDoRepo.save(todo);
	}

	public Long deleteToDoById(Long id) {
		return toDoRepo.deleteToDoById(id);
	}

	public List<ToDo> getAllCompletedTasks(String userName) {
		return toDoRepo.getTasksByStatus(userName, true);
	}

	public List<ToDo> getAllOutstandingTasks(String userName) {
		return toDoRepo.getTasksByStatus(userName, false);
	}
}
