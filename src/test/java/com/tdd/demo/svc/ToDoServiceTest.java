/**
 * 
 */
package com.tdd.demo.svc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
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
	
	private Calendar createDate;
	private Calendar dueDate;
	
	@BeforeEach
	void setCalendar() {
		createDate = Calendar.getInstance();
		dueDate = Calendar.getInstance();
		dueDate.add(Calendar.DATE, 10);
	}
	
	@Test
	void getAllToDos() {
		ToDo toDo1 = new ToDo(100L, "nravuru", "Finish homework!", null, false, createDate.getTime(), dueDate.getTime());
		toDoRepo.save(toDo1);
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		ToDo toDo = toDoSvc.getAllToDos().get(0);
		
		assertEquals(toDo1.getUserName(), toDo.getUserName());			
	}
	
	@Test
	void getAllToDosByUserName() {		
		List<ToDo> list = new ArrayList<>();
		ToDo toDo1 = new ToDo(100L, "kravuru", "Finish homework!", null, false, createDate.getTime(), dueDate.getTime());
		ToDo toDo2 = new ToDo("kravuru", "Paint pictures!", null, false, createDate.getTime(), dueDate.getTime());
		list.add(toDo1);
		list.add(toDo2);
		
		toDoRepo.saveAll(list);
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		List<ToDo> toDos = toDoSvc.getAllToDosByUserName("kravuru");
		
		assertEquals(toDos.size(), 2);			
	}
	
	@Test
	void addToDoTest() {		
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework!", null, false, createDate.getTime(), dueDate.getTime());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		ToDo toDoResp = toDoSvc.addToDo(todo);
		
		assertEquals(toDoResp.getUserName(), "kravuru");			
	}
	
	@Test
	void updateToDoTest() {		
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework!", null, false, createDate.getTime(), dueDate.getTime());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		toDoSvc.addToDo(todo);
		
		ToDo todoResp1 = toDoSvc.getAllToDosByUserName("kravuru").get(0);
		todoResp1.setDescription("Go for a walk!!");
		
		ToDo toDoResp2 = toDoSvc.updateToDo(todoResp1);
		
		assertEquals(toDoResp2.getDescription(), "Go for a walk!!");			
	}
	
	@Test
	void updateToDoCompletedTest() {		
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework!", null, false, createDate.getTime(), dueDate.getTime());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		toDoSvc.addToDo(todo);
		
		ToDo todoResp1 = toDoSvc.getAllToDosByUserName("kravuru").get(0);
		todoResp1.setCompleted(true);
		
		ToDo toDoResp2 = toDoSvc.updateToDo(todoResp1);
		
		assertEquals(toDoResp2.isCompleted(), true);			
	}
	
	@Test
	void deleteToDoTest() {		
		ToDo todo = new ToDo("kravuru", "Finish homework!", null, false, createDate.getTime(), dueDate.getTime());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		toDoSvc.addToDo(todo);
		
		ToDo todoResp1 = toDoSvc.getToDoById(todo.getId());		
		
		toDoSvc.deleteToDoById(todoResp1.getId());
				
		assertNull(toDoSvc.getToDoById(todo.getId()));			
	}
	
	@Test 
	void completedToDosTest() {
		ToDo todo1 = new ToDo(100L, "kravuru", "Finish homework!", null, false, createDate.getTime(), dueDate.getTime());
		ToDo todo2 = new ToDo(101L, "kravuru", "Take dog for a walk!", null, false, createDate.getTime(), dueDate.getTime());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		toDoSvc.addToDo(todo1);
		toDoSvc.addToDo(todo2);
		
		ToDo todoResp = toDoSvc.getAllToDosByUserName("kravuru").get(0);
		todoResp.setCompleted(true);
		
		toDoSvc.updateToDo(todoResp);
		
		List<ToDo> completedList = toDoSvc.getAllCompletedTasks("kravuru");
		
		assertEquals(completedList.size(), 1);
	}
	
	@Test 
	void outstandingToDosTest() {
		ToDo todo1 = new ToDo(100L, "kravuru", "Finish homework!", null, false, createDate.getTime(), dueDate.getTime());
		ToDo todo2 = new ToDo(101L, "kravuru", "Take dog for a walk!", null, false, createDate.getTime(), dueDate.getTime());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		toDoSvc.addToDo(todo1);
		toDoSvc.addToDo(todo2);
		
		ToDo todoResp = toDoSvc.getAllToDosByUserName("kravuru").get(0);
		todoResp.setCompleted(true);
		
		toDoSvc.updateToDo(todoResp);
		
		List<ToDo> completedList = toDoSvc.getAllOutstandingTasks("kravuru");
		
		assertEquals(completedList.size(), 1);
	}
	
	@Test 
	void additionalDetailsTest() {
		ToDo todo1 = new ToDo(100L, "kravuru", "Finish homework!", "Finish Galvanize homework", false, createDate.getTime(), dueDate.getTime());
		ToDo todo2 = new ToDo(101L, "kravuru", "Take dog for a walk!", "My dog really needs to go for a walk", false, createDate.getTime(), dueDate.getTime());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		ToDo todoResp1 = toDoSvc.addToDo(todo1);
		ToDo todoResp2 = toDoSvc.addToDo(todo2);
		
		todoResp1 = toDoSvc.getToDoById(todoResp1.getId());
		todoResp2 = toDoSvc.getToDoById(todoResp2.getId());
						
		assertEquals(todoResp1.getAdditionalDetails(), "Finish Galvanize homework");
		assertEquals(todoResp2.getAdditionalDetails(), "My dog really needs to go for a walk");
	}
	
	@Test 
	void completetdOnTest() {
		ToDo todo1 = new ToDo(100L, "kravuru", "Finish homework!", "Finish Galvanize homework", false, createDate.getTime(), dueDate.getTime());
		ToDo todo2 = new ToDo(101L, "kravuru", "Take dog for a walk!", "My dog really needs to go for a walk", false, createDate.getTime(), dueDate.getTime());
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		ToDo todoResp1 = toDoSvc.addToDo(todo1);
		ToDo todoResp2 = toDoSvc.addToDo(todo2);
		
		todoResp1 = toDoSvc.getToDoById(todoResp1.getId());
		todoResp2 = toDoSvc.getToDoById(todoResp2.getId());
		
		assertEquals(todoResp1.getDueDate(), dueDate.getTime());
		assertEquals(todoResp2.getDueDate(), dueDate.getTime());
	}
}
