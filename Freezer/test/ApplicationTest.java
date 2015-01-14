import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import model.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {
    static Server s = new Server();
	static ArrayList<String> lifts = new ArrayList<String>();
	static ArrayList<Person> persons = new ArrayList<Person>();
	
	@BeforeClass
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
	
	@Test
	public void addPerson(){
		Person p1 = persons.get(0);
		try {
			s.addPerson(p1);
		} catch (Exception e) {}
		assertThat(Server.getMemList()).contains(p1);
	}
	
	@Test
	public void noDuplicates(){
		Person p1 = persons.get(1);
		try {
			s.addPerson(p1);
			s.addPerson(p1);
		} catch (Exception e) {
		}
		ArrayList<Person> mems = new ArrayList<Person>();
		mems = Server.getMemList();
		assertThat(mems).contains(p1);
		mems.remove(p1);
		assertThat(mems.contains(p1)).isEqualTo(false);
	}

	@Test
	public void addLifts(){
		assertThat(Server.getLiftList()).isEmpty();
		Server.setLiftList(lifts);
		assertThat(Server.getLiftList()).isNotEmpty();		
	}
	
	@Test
	public void chooseLift(){
		try {
			persons.get(0).setNextLift(Server.getLiftList().get(0));
		} catch (Exception e) {
		}
		assertThat(persons.get(0).getNextLift()).isEqualTo(Server.getLiftList().get(0));
	}
	
	@Test
	public void noWrongLift(){
		try {
			persons.get(1).setNextLift("foo");
		} catch (Exception e) {
		}
		assertThat(persons.get(1).getNextLift()).isEqualTo("-");
	}
	
	@Test
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
		
		assertThat(p0.getMatches()).contains(p3);
		assertThat(p1.getMatches()).contains(p2);
		assertThat(p2.getMatches()).contains(p1);
		assertThat(p3.getMatches()).contains(p0);
		
		assertThat(p4.getMatches()).isEmpty();
		
		assertThat(p0.getMatches().size()).isEqualTo(1);
		assertThat(p1.getMatches().size()).isEqualTo(1);
		assertThat(p2.getMatches().size()).isEqualTo(1);
		assertThat(p3.getMatches().size()).isEqualTo(1);
	}

	@Test
	public void find(){
		assertThat(s.findPerson(Server.getMemList().get(0).getName())).isEqualTo(Server.getMemList().get(0));
	}
	
	@Test
	public void becomeFriends(){
		Person p0 = persons.get(0);
		Person p1 = persons.get(1);
		
		assertThat(p0.getFriends().contains(p1)).isEqualTo(false);
		assertThat(p1.getFriends().contains(p0)).isEqualTo(false);
		assertThat(p0.getRequests().contains(p1)).isEqualTo(false);
		assertThat(p1.getRequests().contains(p0)).isEqualTo(false);
		
		try{
			p0.friendrequest(p1);
		} catch(Exception e){}
		
		assertThat(p1.getRequests()).contains(p0);
		assertThat(p0.getRequests().contains(p1)).isEqualTo(false);
		assertThat(p0.getFriends().contains(p1)).isEqualTo(false);
		assertThat(p1.getFriends().contains(p0)).isEqualTo(false);
		
		try{
			p1.friendrequest(p0);
		} catch(Exception e){}
		
		assertThat(p1.getRequests()).contains(p0);
		assertThat(p0.getRequests()).contains(p1);
		assertThat(p0.getFriends()).contains(p1);
		assertThat(p1.getFriends()).contains(p0);
	}
	
	@Test
	public void noDoubleRequests(){
		Person p2 = persons.get(2);
		Person p3 = persons.get(3);
		
		try{
			p2.friendrequest(p3);
			p3.friendrequest(p2);
		} catch(Exception e){}
		
		assertThat(p2.getFriends()).contains(p3);
		assertThat(p3.getFriends()).contains(p2);
		
		try{
			p2.friendrequest(p3);
			p3.friendrequest(p2);
		} catch(Exception e){}
		
		assertThat(p2.getFriends().size()).isEqualTo(1);
		assertThat(p3.getFriends().size()).isEqualTo(1);
	}
	
	@Test
	public void noOwnRequests(){
		Person p4 = persons.get(4);
		
		try{
			p4.friendrequest(p4);
		} catch(Exception e){}
		
		assertThat(p4.getFriends()).isEmpty();
	}
	
    @Test
    public void simpleCheck() {
        int[] a = {1,2};
        assertThat(a).isNotEmpty();
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }

    

}
