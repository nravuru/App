/**
 * 
 */
package com.tdd.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tdd.demo.entity.ToDo;

/**
 * @author naresh.ravurumckesson.com
 *
 */
@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

	public ToDo findToDoById(Long id);
	public List<ToDo> findAllToDosByUserName(String userName);
	public Long deleteToDoById(Long id);
	@Query("SELECT t FROM ToDo t WHERE t.userName = ?1 and t.completed = true")
	public List<ToDo> getCompletedTasks(String userName);
}
