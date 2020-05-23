/**
 * 
 */
package com.tdd.demo.svc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tdd.demo.entity.ToDo;
import com.tdd.demo.repo.ToDoRepository;

/**
 * @author naresh.ravurumckesson.com
 *
 */
@SpringBootTest
public class ToDoServiceTest {

	@Autowired
	private ToDoRepository toDoRepo;
	
	@Test
	void getAllToDos() {		
		toDoRepo.deleteAll();
		
		ToDo toDo1 = new ToDo(100L, "nravuru", "Finish homework!", new Date(), new Date());
		toDoRepo.save(toDo1);
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		ToDo toDo = toDoSvc.getAllToDos().get(0);
		
		assertEquals(toDo1.getUserName(), toDo.getUserName());			
	}
	
	@Test
	void getAllToDosByUserName() {		
		toDoRepo.deleteAll();
		
		List<ToDo> list = new ArrayList<>();
		ToDo toDo1 = new ToDo(100L, "kravuru", "Finish homework!", new Date(), new Date());
		ToDo toDo2 = new ToDo(100L, "kravuru", "Paint pictures!", new Date(), new Date());
		list.add(toDo1);
		list.add(toDo2);
		
		toDoRepo.saveAll(list);
		
		ToDoService toDoSvc = new ToDoService(toDoRepo);
		List<ToDo> toDos = toDoSvc.getAllToDosByUserName();
		
		assertEquals(toDos.size(), 2);			
	}
}
