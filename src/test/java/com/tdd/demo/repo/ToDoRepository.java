/**
 * 
 */
package com.tdd.demo.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.tdd.demo.entity.ToDo;

/**
 * @author naresh.ravurumckesson.com
 *
 */
@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

}
