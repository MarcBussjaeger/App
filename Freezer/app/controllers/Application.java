package controllers;

import play.*;
import play.mvc.*;
import model.*;

import java.util.ArrayList;

public class Application extends Controller {
    private static Server s;
    private static ArrayList<String> lifts = new ArrayList<String>();
    private static ArrayList<Person> persons = new ArrayList<Person>();

    public static Result index() {
        return ok(views.html.index.render("Welcome to Freezer!"));
    }
    
    public static Result signIn() {
        String m=null;
        Person p= new Person("SkiingMarc",21,Gen.male,Gen.female);

        if(persons.isEmpty()){  
            persons.add(new Person("SkiingSusi",24,Gen.female,Gen.male));
            persons.add(new Person("BoarderGirl",28,Gen.female,Gen.male));
            persons.add(new Person("Mrs.Snow",33,Gen.female,Gen.male));
            persons.add(new Person("Snowcate",21,Gen.female,Gen.male));
        }
        persons.add(p);

        s.setLiftList(lifts);
        s.setMemList(persons);
        p.setMatches(Server.match(p));
        m = "Hello "+p.getName()+" your 'snowmates': ";
        
        for(Person p0:p.getMatches()){
            m += p0.getName()+", ";
        }
        return ok(views.html.mainMenu.render(m));
    }
    
    public static Result friends(){
        Person p0 = new Person("SkiingMarc",21,Gen.male,Gen.female);
        Person p1 = new Person("SkiingSusi",24,Gen.female,Gen.male);
        persons.add(p0);
        persons.add(p1);
        s.setMemList(persons);
        
        try{
            p0.friendrequest(p1);
            p1.friendrequest(p0);
        }catch(Exception e){}
        if(p0.getFriends().contains(p1)){
                return ok(views.html.friends.render("Congratulations you are now friends"));
        }
        return ok(views.html.friends.render("Something went wrong"));
    }
    
}
