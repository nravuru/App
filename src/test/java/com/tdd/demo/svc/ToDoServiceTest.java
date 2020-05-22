/**
 * 
 */
package com.tdd.demo.svc;

import java.util.Date;

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
		ToDo toDo1 = new ToDo(100L, "nravuru", "Finish homework!", new Date(), new Date());
		toDoRepo.save(toDo1);
	}
	
}
