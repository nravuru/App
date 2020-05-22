/**
 * 
 */
package com.tdd.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<List<ToDo>> getAllToDos() {
		return new ResponseEntity<>(toDoService.getAllToDos(), HttpStatus.OK);
	}
}
