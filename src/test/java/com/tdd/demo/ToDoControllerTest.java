/**
 * 
 */
package com.tdd.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tdd.demo.entity.ToDo;
import com.tdd.demo.svc.ToDoService;

/**
 * @author naresh.ravurumckesson.com
 *
 */
@WebMvcTest
@ExtendWith(SpringExtension.class)
public class ToDoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ToDoService toDoSvc;
	
	@Test
	void testToDos() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo(1, "Fill Timesheet", new Date(), new Date()));
		todoList.add(new ToDo(2, "Complete homework", new Date(), new Date()));
		when(toDoSvc.findAll()).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2))).andDo(print());
	}
}


