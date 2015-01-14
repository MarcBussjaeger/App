package model;

import java.util.ArrayList;



public class Person {
	private String nextLift, name;
	private int age;
	private Gen gender, preference;
	private ArrayList<Person> matches = new ArrayList<Person>();
	private ArrayList<Person> friends = new ArrayList<Person>();
	private ArrayList<Person> requests = new ArrayList<Person>();
	
	public Person(String name, int age, Gen gender, Gen preference) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.preference = preference;
		this.nextLift = "-";
		matches = Server.match(this);
	}
	
	public String getNextLift() {
		return nextLift;
	}
	public void setNextLift(String nextLift) throws Exception {
		if(Server.getLiftList().contains(nextLift)){
			this.nextLift = nextLift;
		} else {
			throw new Exception("no such lift");
		}
		//when new lift is set, calculate new matches
		matches = Server.match(this);
	}
	public void friendrequest(Person p) throws Exception{
		if(!(p.equals(this))&&(!p.getRequests().contains(this))){
			p.getRequests().add(this);
		} else {
			throw new Exception("already sent a friendrequest");
		}
		if(this.requests.contains(p)){
			p.getFriends().add(this);
			this.friends.add(p);
		}
	}

	
	public ArrayList<Person> getRequests() {
		return requests;
	}
	public void setRequests(ArrayList<Person> requests) {
		this.requests = requests;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Gen getGender() {
		return gender;
	}
	public void setGender(Gen gender) {
		this.gender = gender;
	}
	public Gen getPreference() {
		return preference;
	}
	public void setPreference(Gen preference) {
		this.preference = preference;
	}
	public ArrayList<Person> getMatches() {
		return matches;
	}
	public void setMatches(ArrayList<Person> matches) {
		this.matches = matches;
	}
	public ArrayList<Person> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<Person> friends) {
		this.friends = friends;
	}
}
