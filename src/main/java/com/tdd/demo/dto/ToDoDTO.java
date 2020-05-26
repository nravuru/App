/**
 * 
 */
package com.tdd.demo.dto;

/**
 * @author naresh.ravurumckesson.com
 *
 */

import java.util.Date;


/**
 * @author naresh.ravurumckesson.com
 *
 */

public class ToDoDTO {
	
	private Long id;
	private String userName;
	private String description;
	private String additionalDetails;
	private Boolean completed;
	private Date createDate;
	private Date dueDate;

	public ToDoDTO() {
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the additionalDetails
	 */
	public String getAdditionalDetails() {
		return additionalDetails;
	}

	/**
	 * @param additionalDetails the additionalDetails to set
	 */
	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	/**
	 * @return the completed
	 */
	public Boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ToDoDTO [id=" + id + ", userName=" + userName + ", description=" + description + ", completed=" + completed
				+ ", createDate=" + createDate + ", dueDate=" + dueDate + "]";
	}

}

