/**
 * 
 */
package com.tdd.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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
	void testToDosAccessProtected() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/all")).andExpect(status().isUnauthorized());
	}
	
	@Test
	void testToDos() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo(100L, "nravuru", "Fill Timesheet", null, false, new Date(), new Date()));
		todoList.add(new ToDo(101L, "kravuru", "Complete homework", null,false, new Date(), new Date()));
		when(toDoSvc.getAllToDos()).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/all").with(httpBasic("visitor", "visitor123")))
			.andExpect(jsonPath("$", hasSize(2)));			
	}
	
	@Test
	void testToDosForbidden() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo(100L, "nravuru", "Fill Timesheet", null, false, new Date(), new Date()));
		todoList.add(new ToDo(101L, "kravuru", "Complete homework", null,false, new Date(), new Date()));
		when(toDoSvc.getAllToDos()).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/all").with(httpBasic("user", "user123")))
			.andExpect(status().isForbidden());		
	}
	
	@Test
	void testToDosByUserName() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo(100L, "kravuru", "Fill Timesheet", null, false, new Date(), new Date()));
		todoList.add(new ToDo(101L, "kravuru", "Complete homework", null, false, new Date(), new Date()));
		
		when(toDoSvc.getAllToDosByUserName(anyString())).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{username}", "kravuru").with(httpBasic("visitor", "visitor123")))
			.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	void testToDosByUserNameBadCreds() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo(100L, "kravuru", "Fill Timesheet", null, false, new Date(), new Date()));
		todoList.add(new ToDo(101L, "kravuru", "Complete homework", null, false, new Date(), new Date()));
		
		when(toDoSvc.getAllToDosByUserName(anyString())).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{username}", "kravuru").with(httpBasic("user", "visitor123")))
			.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
	}
	
	@Test
	void testToDosByUserNameBadCred() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo(100L, "kravuru", "Fill Timesheet", null, false, new Date(), new Date()));
		todoList.add(new ToDo(101L, "kravuru", "Complete homework", null, false, new Date(), new Date()));
		
		when(toDoSvc.getAllToDosByUserName(anyString())).thenReturn(todoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{username}", "kravuru").with(httpBasic("visitor", "user123")))
			.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
	}
	
	@Test
	void testAddToDo() throws Exception {
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		
		when(toDoSvc.addToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/todos/add").with(httpBasic("user", "user123"))
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.userName").value("kravuru")).andDo(print());
	}
	
	@Test
	void testAddToDoForbidden() throws Exception {
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		
		when(toDoSvc.addToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/todos/add").with(httpBasic("visitor", "visitor123")))
				.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	void testUpdateToDo() throws Exception {
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		todo.setDescription("Go for a walk!!");
				
		when(toDoSvc.updateToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/todos/update").with(httpBasic("user", "user123"))
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description").value("Go for a walk!!"));
	}
	
	@Test
	void testUpdateToDoForbidden() throws Exception {
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		todo.setDescription("Go for a walk!!");
				
		when(toDoSvc.updateToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/todos/update").with(httpBasic("visitor", "visitor123")))
				.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	void testUpdateToDoCompleted() throws Exception {
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		todo.setCompleted(true);
				
		when(toDoSvc.updateToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/todos/update").with(httpBasic("user", "user123"))
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.completed").value(true));
	}
	
	@Test
	void testUpdateToDoCompletedForbidden() throws Exception {
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		todo.setCompleted(true);
				
		when(toDoSvc.updateToDo(any())).thenReturn(todo);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/todos/update").with(httpBasic("visitor", "visitor123")))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	void testDeleteToDo() throws Exception {
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		todo.setId(100L);
		
		when(toDoSvc.deleteToDoById(anyLong())).thenReturn(todo.getId());
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/todos/delete/{id}", 100L).with(httpBasic("user", "user123"))
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(todo))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").value("Record deleted"));
	}
	
	@Test
	void testDeleteToDoForbidden() throws Exception {
		ToDo todo = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		todo.setId(100L);
		
		when(toDoSvc.deleteToDoById(anyLong())).thenReturn(todo.getId());
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/todos/delete/{id}", 100L).with(httpBasic("visitor", "visitor123")))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	void testCompletedToDos() throws Exception {
		ToDo todo1 = new ToDo(100L, "kravuru", "Finish homework", null, true, new Date(), new Date());
		ToDo todo2 = new ToDo(101L, "kravuru", "Take dog for a walk", null, true, new Date(), new Date());
		List<ToDo> completedTasks = new ArrayList<>();
		
		completedTasks.add(todo1);
		completedTasks.add(todo2);
		
		when(toDoSvc.getAllCompletedTasks(anyString())).thenReturn(completedTasks);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{userName}/{completeBln}", "kravuru", "true").with(httpBasic("user", "user123"))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	void testCompletedToDosForbidden() throws Exception {
		ToDo todo1 = new ToDo(100L, "kravuru", "Finish homework", null, true, new Date(), new Date());
		ToDo todo2 = new ToDo(101L, "kravuru", "Take dog for a walk", null, true, new Date(), new Date());
		List<ToDo> completedTasks = new ArrayList<>();
		
		completedTasks.add(todo1);
		completedTasks.add(todo2);
		
		when(toDoSvc.getAllCompletedTasks(anyString())).thenReturn(completedTasks);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{userName}/{completeBln}", "kravuru", "true").with(httpBasic("visitor", "visitor123")))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	void testOutstandingToDos() throws Exception {
		ToDo todo1 = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		ToDo todo2 = new ToDo(101L, "kravuru", "Take dog for a walk", null, false, new Date(), new Date());
		List<ToDo> completedTasks = new ArrayList<>();
		
		completedTasks.add(todo1);
		completedTasks.add(todo2);
		
		when(toDoSvc.getAllOutstandingTasks(anyString())).thenReturn(completedTasks);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{userName}/{completeBln}", "kravuru", "false").with(httpBasic("user", "user123"))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	void testOutstandingToDosForbidden() throws Exception {
		ToDo todo1 = new ToDo(100L, "kravuru", "Finish homework", null, false, new Date(), new Date());
		ToDo todo2 = new ToDo(101L, "kravuru", "Take dog for a walk", null, false, new Date(), new Date());
		List<ToDo> completedTasks = new ArrayList<>();
		
		completedTasks.add(todo1);
		completedTasks.add(todo2);
		
		when(toDoSvc.getAllOutstandingTasks(anyString())).thenReturn(completedTasks);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{userName}/{completeBln}", "kravuru", "false").with(httpBasic("visitor", "visitor123")))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	void testAdditionalDetails() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo("kravuru", "Fill Timesheet", "Submit my timesheet without fail", false, new Date(), new Date()));
		
		when(toDoSvc.getAllToDosByUserName(anyString())).thenReturn(todoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{username}", "kravuru").with(httpBasic("visitor", "visitor123"))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(todoList))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.[0].additionalDetails").value("Submit my timesheet without fail"));
	}
	
	@Test
	void testAdditionalDetailsForbidden() throws Exception {
		List<ToDo> todoList = new ArrayList<>();
		todoList.add(new ToDo("kravuru", "Fill Timesheet", "Submit my timesheet without fail", false, new Date(), new Date()));
		
		when(toDoSvc.getAllToDosByUserName(anyString())).thenReturn(todoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/todos/{username}", "kravuru").with(httpBasic("user", "user123")))
			.andExpect(status().is(HttpStatus.FORBIDDEN.value()));
	}
}


