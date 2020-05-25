/**
 * 
 */
package com.tdd.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
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
		todoList.add(new ToDo("nravuru", "Fill Timesheet", false, new Date(), new Date()));
		todoList.add(new ToDo("kravuru", "Complete homework", false, new Date(), new Date()));
		when(toDoSvc.getAllToDos()).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	void testToDosByUserName() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo("kravuru", "Fill Timesheet", false, new Date(), new Date()));
		todoList.add(new ToDo("kravuru", "Complete homework", false, new Date(), new Date()));
		
		when(toDoSvc.getAllToDosByUserName(anyString())).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{username}", "kravuru").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	void testAddToDo() throws Exception {
		ToDo todo = new ToDo("kravuru", "Finish homework", false, new Date(), new Date());
		
		when(toDoSvc.addToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/todos/add")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.userName").value("kravuru"));		
	}
	
	@Test
	void testUpdateToDo() throws Exception {
		ToDo todo = new ToDo("kravuru", "Finish homework", false, new Date(), new Date());
		todo.setDescription("Go for a walk!!");
				
		when(toDoSvc.updateToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/todos/update")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description").value("Go for a walk!!"));
	}
	
	@Test
	void testUpdateToDoCompleted() throws Exception {
		ToDo todo = new ToDo("kravuru", "Finish homework", false, new Date(), new Date());
		todo.setCompleted(true);
				
		when(toDoSvc.updateToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/todos/update")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.completed").value(true));
	}
	
	@Test
	void testDeleteToDo() throws Exception {
		ToDo todo = new ToDo("kravuru", "Finish homework", false, new Date(), new Date());
		todo.setId(100L);
		
		when(toDoSvc.deleteToDoById(anyLong())).thenReturn(todo.getId());
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/todos/delete/{id}", 100L)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").value("Record deleted"));
	}
	
	@Test
	void testCompletedToDos() throws Exception {
		ToDo todo1 = new ToDo("kravuru", "Finish homework", true, new Date(), new Date());
		ToDo todo2 = new ToDo("kravuru", "Take dog for a walk", true, new Date(), new Date());
		List<ToDo> completedTasks = new ArrayList<>();
		
		completedTasks.add(todo1);
		completedTasks.add(todo2);
		
		when(toDoSvc.getAllCompletedTasks(anyString())).thenReturn(completedTasks);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{userName}/{completeBln}", "kravuru", "true")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2))).andDo(print());
	}
	
	@Test
	void testOutstandingToDos() throws Exception {
		ToDo todo1 = new ToDo("kravuru", "Finish homework", false, new Date(), new Date());
		ToDo todo2 = new ToDo("kravuru", "Take dog for a walk", false, new Date(), new Date());
		List<ToDo> completedTasks = new ArrayList<>();
		
		completedTasks.add(todo1);
		completedTasks.add(todo2);
		
		when(toDoSvc.getAllOutstandingTasks(anyString())).thenReturn(completedTasks);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{userName}/{completeBln}", "kravuru", "false")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2))).andDo(print());
	}
}


