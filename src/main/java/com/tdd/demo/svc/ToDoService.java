/**
 * 
 */
package com.tdd.demo.svc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tdd.demo.entity.ToDo;

/**
 * @author naresh.ravurumckesson.com
 *
 */
@Service
public class ToDoService {

	public List<ToDo> findAll() {
		return new ArrayList<>();
	}

}
