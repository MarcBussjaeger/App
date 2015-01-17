package model;

import java.util.ArrayList;

public class StdForm{
	public String name, gender, preference, age, password, verification;
	public String ownName;
	
	public String getPassword(){
		return password;
	}
	public String getVerification(){
		return verification;
	}
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
	public String getPerson(){
		return ownName;
	}
}
