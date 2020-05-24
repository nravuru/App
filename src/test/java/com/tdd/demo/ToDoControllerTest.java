/**
 * 
 */
package com.tdd.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ToDoService toDoSvc;
	
	@Test
	void testToDos() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo("nravuru", "Fill Timesheet", new Date(), new Date()));
		todoList.add(new ToDo("kravuru", "Complete homework", new Date(), new Date()));
		when(toDoSvc.getAllToDos()).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2))).andDo(print());
	}
	
	@Test
	void testToDosByUserName() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo("kravuru", "Fill Timesheet", new Date(), new Date()));
		todoList.add(new ToDo("kravuru", "Complete homework", new Date(), new Date()));
		
		when(toDoSvc.getAllToDosByUserName(anyString())).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{username}", "kravuru").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2))).andDo(print());
	}
	
	@Test
	void testAddToDo() throws Exception {
		ToDo todo = new ToDo("kravuru", "Finish homework", new Date(), new Date());
		
		when(toDoSvc.addToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/todos/add")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.userName").value("kravuru")).andDo(print());		
	}
	
	@Test
	void testUpdateToDo() throws Exception {
		ToDo todo = new ToDo("kravuru", "Finish homework", new Date(), new Date());
		todo.setDescription("Go for a walk!!");
				
		when(toDoSvc.updateToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/todos/update")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description").value("Go for a walk!!")).andDo(print());
	}
}


