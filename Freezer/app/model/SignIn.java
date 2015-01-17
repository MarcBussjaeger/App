package model;

import java.util.ArrayList;

public class SignIn{
	private Server s = new Server();

	public String name, gender, preference, age;
	
	public String getGender(){
		return gender;
	}
	public String getPreference(){
		return preference;
	}
	public String getAge(){
		return age;
	}
	public String getName(){
		return name;
	}
}
