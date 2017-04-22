package de.test.mqtt;

public class Person implements java.io.Serializable {
	
	public String surname;
	public String lastname;

	public Person(String surname, String lastname) {
		super();
		this.surname = surname;
		this.lastname = lastname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
}
