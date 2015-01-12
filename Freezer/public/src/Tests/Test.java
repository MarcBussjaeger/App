package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import Enums.Gen;
import ServerPerson.Person;
import ServerPerson.Server;

public class Test {
	static Server s = new Server();
	static ArrayList<String> lifts = new ArrayList<String>();
	static ArrayList<Person> persons = new ArrayList<Person>();
	
	@org.junit.BeforeClass
	public static void start(){
		persons.add(new Person("SnowCate", 21, Gen.female, Gen.female));
		persons.add(new Person("BoarderSusi", 24, Gen.female, Gen.male));
		persons.add(new Person("SkiingMarc", 21, Gen.male, Gen.female));
		persons.add(new Person("Crystal", 29, Gen.female, Gen.female));
		persons.add(new Person("MrClause",30,Gen.male,Gen.male));
		lifts.add("Hexenboden");
		lifts.add("Übungshang");
		lifts.add("Zürsersee");
	}
	
	@org.junit.Test
	public void addPerson(){
		Person p1 = persons.get(0);
		try {
			s.addPerson(p1);
		} catch (Exception e) {}
		assertTrue(Server.getMemList().contains(p1));
	}
	
	@org.junit.Test
	public void noDuplicates(){
		Person p1 = persons.get(1);
		try {
			s.addPerson(p1);
			s.addPerson(p1);
		} catch (Exception e) {
		}
		ArrayList<Person> mems = new ArrayList<Person>();
		mems = Server.getMemList();
		assertTrue(mems.contains(p1));
		mems.remove(p1);
		assertFalse(mems.contains(p1));
	}

	@org.junit.Test
	public void addLifts(){
		assertTrue(Server.getLiftList().isEmpty());
		Server.setLiftList(lifts);
		assertFalse(Server.getLiftList().isEmpty());		
	}
	
	@org.junit.Test
	public void chooseLift(){
		try {
			persons.get(0).setNextLift(Server.getLiftList().get(0));
		} catch (Exception e) {
		}
		assertTrue(persons.get(0).getNextLift().equals(Server.getLiftList().get(0)));
	}
	
	@org.junit.Test
	public void noWrongLift(){
		try {
			persons.get(1).setNextLift("foo");
		} catch (Exception e) {
		}
		assertTrue(persons.get(1).getNextLift().equals("-"));
	}
	
	@org.junit.Test
	public void match(){
		Person p0 = persons.get(0);
		Person p1 = persons.get(1);
		Person p2 = persons.get(2);
		Person p3 = persons.get(3);
		Person p4 = persons.get(4);
		
		Server.setMemList(persons);
		
		p0.setMatches(Server.match(p0));
		p1.setMatches(Server.match(p1));
		p2.setMatches(Server.match(p2));
		p3.setMatches(Server.match(p3));
		p4.setMatches(Server.match(p4));
		
		assertTrue(p0.getMatches().contains(p3));
		assertTrue(p1.getMatches().contains(p2));
		assertTrue(p2.getMatches().contains(p1));
		assertTrue(p3.getMatches().contains(p0));
		
		assertTrue(p4.getMatches().isEmpty());
		
		assertEquals(p0.getMatches().size(),1);
		assertEquals(p1.getMatches().size(),1);
		assertEquals(p2.getMatches().size(),1);
		assertEquals(p3.getMatches().size(),1);
	}

	public void find(){
		assertTrue(s.findPerson(Server.getMemList().get(0).getName()).equals(Server.getMemList().get(0)));
	}
	
}
