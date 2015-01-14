package model;

import java.util.ArrayList;


public class Server {
	private static ArrayList<Person> memList = new ArrayList<Person>();
	private static ArrayList<String> liftList = new ArrayList<String>();
	
	public void addPerson(Person p1) throws Exception{
		for(Person p:memList){
			if(p1.getName().equals(p.getName())){
				throw new Exception("username already exists");
			}
		}
		memList.add(p1);
	}
	public void addPerson(String name, int age, Gen gender, Gen preference) throws Exception{
		for(Person p:memList){
			if(name.equals(p.getName())){
				throw new Exception("username already exists");
			}
		}
		memList.add(new Person(name,age,gender,preference));
	}

	public Person findPerson(String name){
		for(Person p:memList){
			if(p.getName().equalsIgnoreCase(name)){
				return p;
			}
		}
		return null;
	}
	
	public static ArrayList<Person> match(Person p1){
		ArrayList<Person> matches = new ArrayList<Person>();
		
		for(Person p:memList){
			if(p.getGender().equals(p1.getPreference())&& !p.equals(p1) &&
					p.getPreference().equals(p1.getGender())){
				matches.add(p);
			}
		}
		return matches;
	}
	
	public static ArrayList<String> getLiftList() {
		return liftList;
	}
	public static void setLiftList(ArrayList<String> liftList) {
		Server.liftList = liftList;
	}

	public static ArrayList<Person> getMemList() {
		return memList;
	}
	public static void setMemList(ArrayList<Person> memList) {
		Server.memList = memList;
	}
	
	
}
