/**
 * 
 */
package com.tdd.demo.svc;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tdd.demo.entity.ToDo;
import com.tdd.demo.repo.ToDoRepository;

/**
 * @author naresh.ravurumckesson.com
 *
 */
@Transactional
@SpringBootTest
public class ToDoServiceTest {

	@Autowired
	private ToDoRepository toDoRepo;
	
	@Test
	void getAllToDos() {
		ToDo toDo1 = new ToDo("nravuru", "Finish homework!", false, new Date(), new Date());
		toDoRepo.save(toDo1);
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		ToDo toDo = toDoSvc.getAllToDos().get(0);
		
		assertEquals(toDo1.getUserName(), toDo.getUserName());			
	}
	
	@Test
	void getAllToDosByUserName() {		
		List<ToDo> list = new ArrayList<>();
		ToDo toDo1 = new ToDo("kravuru", "Finish homework!", false, new Date(), new Date());
		ToDo toDo2 = new ToDo("kravuru", "Paint pictures!", false, new Date(), new Date());
		list.add(toDo1);
		list.add(toDo2);
		
		toDoRepo.saveAll(list);
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		List<ToDo> toDos = toDoSvc.getAllToDosByUserName("kravuru");
		
		assertEquals(toDos.size(), 2);			
	}
	
	@Test
	void addToDoTest() {		
		ToDo todo = new ToDo("kravuru", "Finish homework!", false, new Date(), new Date());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		ToDo toDoResp = toDoSvc.addToDo(todo);
		
		assertEquals(toDoResp.getUserName(), "kravuru");			
	}
	
	@Test
	void updateToDoTest() {		
		ToDo todo = new ToDo("kravuru", "Finish homework!", false, new Date(), new Date());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		toDoSvc.addToDo(todo);
		
		ToDo todoResp1 = toDoSvc.getAllToDosByUserName("kravuru").get(0);
		todoResp1.setDescription("Go for a walk!!");
		
		ToDo toDoResp2 = toDoSvc.updateToDo(todoResp1);
		
		assertEquals(toDoResp2.getDescription(), "Go for a walk!!");			
	}
	
	@Test
	void updateToDoCompletedTest() {		
		ToDo todo = new ToDo("kravuru", "Finish homework!", false, new Date(), new Date());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		toDoSvc.addToDo(todo);
		
		ToDo todoResp1 = toDoSvc.getAllToDosByUserName("kravuru").get(0);
		todoResp1.setCompleted(true);
		
		ToDo toDoResp2 = toDoSvc.updateToDo(todoResp1);
		
		assertEquals(toDoResp2.isCompleted(), true);			
	}
	
	@Test
	void deleteToDoTest() {		
		ToDo todo = new ToDo("kravuru", "Finish homework!", false, new Date(), new Date());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		toDoSvc.addToDo(todo);
		
		ToDo todoResp1 = toDoSvc.getToDoById(todo.getId());		
		
		toDoSvc.deleteToDoById(todoResp1.getId());
				
		assertNull(toDoSvc.getToDoById(todo.getId()));			
	}
}
