package controllers;

import play.*;
import play.data.*;
import play.mvc.*;
import model.*;

import java.util.ArrayList;

public class Application extends Controller {
    private static ArrayList<String> lifts = new ArrayList<String>();
    private static ArrayList<Person> persons = new ArrayList<Person>();
    
	static Form<StdForm> stdForm = Form.form(StdForm.class);

    public static Result index() {
		if(lifts.isEmpty()){
			lifts.add("hexenboden");
			lifts.add("zürsersee");
			lifts.add("übungshang");
			Server.setLiftList(lifts);
		}
        return ok(views.html.index.render("Welcome to Freezer!"));
    }

    public static Result signIn() {		
		return ok(views.html.signIn.render(Server.getMemList(), stdForm,""));
	}
	
	public static Result signUp(){
		return ok(views.html.signUp.render(Server.getMemList(), stdForm,""));
	}
	
	public static Result createProfile(){
		Form<StdForm> filled = stdForm.bindFromRequest();
		if((filled.hasErrors())||(Server.findPerson(filled.get().getName().toLowerCase())!=null)){
			return badRequest(views.html.signUp.render(Server.getMemList(), stdForm,"Info:something went wrong at the last login"));
		}else {
			Gen gender, preference;
			
			if(filled.get().getGender().equalsIgnoreCase(Gen.female.toString())){
				gender = Gen.female;
			} else if(filled.get().getGender().equalsIgnoreCase(Gen.male.toString())){
				gender = Gen.male;
			} else {
				return badRequest(views.html.signUp.render(Server.getMemList(), stdForm,"Info: the genders should be 'male' or 'female'"));
			}
			if(filled.get().getPreference().equalsIgnoreCase(Gen.female.toString())){
				preference = Gen.female;
			} else if(filled.get().getPreference().equalsIgnoreCase(Gen.male.toString())){
				preference = Gen.male;
			} else {
				return badRequest(views.html.signUp.render(Server.getMemList(), stdForm,"Info: the genders should be 'male' or 'female'"));
			}
			Person p = new Person(filled.get().getName().toLowerCase(),Integer.parseInt(filled.get().getAge()),gender,preference);
			if(filled.get().getPassword().equals(filled.get().getVerification())){
				p.setPassword(filled.get().getPassword());
			} else {
				return badRequest(views.html.signUp.render(Server.getMemList(), stdForm,"Info: the password and the verification didn't match"));
			}
			try{
				Server.getMemList().add(p);
			} catch(Exception e){
				return badRequest(views.html.signUp.render(Server.getMemList(), stdForm,"Info: person not added to the server"));
			}
			
			return ok(views.html.profile.render(p,p.getName(),stdForm,""));	
		}
	}
	
	public static Result toProfile(){
		Form<StdForm> filled = stdForm.bindFromRequest();
		if((filled.hasErrors())||(Server.findPerson(filled.get().getName().toLowerCase())==null)){
			return badRequest(views.html.signIn.render(Server.getMemList(), stdForm,"Info: something went wrong at the last login"));
		}else {
			if(filled.get().getPassword().equals(Server.findPerson(filled.get().getName().toLowerCase()).getPassword())){
				return ok(views.html.profile.render(Server.findPerson(filled.get().getName().toLowerCase()),filled.get().getName().toLowerCase(),stdForm,""));
			} else {
				return badRequest(views.html.signIn.render(Server.getMemList(), stdForm,"Info: wrong password"));
			}	
		}
	}
	
	public static Result sendRequest(){
		Form<StdForm> filled = stdForm.bindFromRequest();
		if((filled.hasErrors())||(Server.findPerson(filled.get().getName().toLowerCase())==null)||(filled.get().getPerson()==null)){
			return badRequest(views.html.signIn.render(Server.getMemList(),stdForm,"Info: something went totally wrong"));
		}else {
			try{
				Server.findPerson(filled.get().getPerson()).friendrequest(Server.findPerson(filled.get().getName().toLowerCase()));
			}catch (Exception e){
				return badRequest(views.html.profile.render(Server.findPerson(filled.get().getPerson()),filled.get().getPerson(),stdForm,"Info: something went wrong with the friendrequest"));
			}
			return ok(views.html.profile.render(Server.findPerson(filled.get().getPerson()),filled.get().getPerson(),stdForm,""));	
		}
	}
	
	public static Result setLift(){
		Form<StdForm> filled = stdForm.bindFromRequest();
		if((filled.hasErrors())||(filled.get().getPerson()==null)){
			return badRequest(views.html.signIn.render(Server.getMemList(),stdForm,"Info: something went totally wrong"));
		}else {
			try{
				Server.findPerson(filled.get().getPerson()).setNextLift(filled.get().getName().toLowerCase());
			} catch (Exception e){
				return badRequest(views.html.profile.render(Server.findPerson(filled.get().getPerson()),filled.get().getPerson(),stdForm,"Info: no such lift"));
			}
			return ok(views.html.profile.render(Server.findPerson(filled.get().getPerson()),filled.get().getPerson(),stdForm,""));	
		}
	}
}
