package src.commons;

/**
 * 
 * The class thst is used to represent a person, actor or director, in the Movida application.
 * 
 * A person is uniquely identified by the case-insensitive name, without leading and trailing spaces, without double spaces. 
 * 
 * Simplification: <code> name </code> is used to store the full name (first and last name).
 * 
 * The class can be modified or extended but it must implement the getName () method.
 * 
 */
public class Person {

	private String name;
	private Integer participate;
	
	public Person(String name) {
		this.name = name;
		this.participate = 0;
	}
	
	public String getName(){
		return this.name;
	}
	public Integer getParticipate(){
		return this.participate;
	}
	public void increase(){
		this.participate = this.participate + 1;
	}
	public void decrease(){
		this.participate = this.participate - 1;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		return this.name.equals(other.name);
	}

	public int hashCode() {
		return this.name.hashCode();
	}
}
