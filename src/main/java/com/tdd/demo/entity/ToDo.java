/**
 * 
 */
package com.tdd.demo.entity;

import java.util.Date;

/**
 * @author naresh.ravurumckesson.com
 *
 */
public class ToDo {

	private int id;
	private String description;
	private Date createDate;
	private Date dueDate;

	public ToDo(int id, String description, Date createDate, Date dueDate) {
		this.id = id;
		this.description = description;
		this.createDate = createDate;
		this.dueDate = dueDate;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
