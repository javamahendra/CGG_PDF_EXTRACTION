package com.app;

public class TSPSC {

	private String TSPSCID;
	private String HALL;
	private String Name;
	private String Birth;
	private String Gender;

	public String getTSPSCID() {
		return TSPSCID;
	}

	public void setTSPSCID(String tSPSCID) {
		TSPSCID = tSPSCID;
	}

	public String getHALL() {
		return HALL;
	}

	public void setHALL(String hALL) {
		HALL = hALL;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getBirth() {
		return Birth;
	}

	public void setBirth(String birth) {
		Birth = birth;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public TSPSC() {
		super();
	}

	public TSPSC(String tSPSCID) {
		super();
		TSPSCID = tSPSCID;
	}

	@Override
	public String toString() {
		return "TSPSC [TSPSCID=" + TSPSCID + ", HALL=" + HALL + ", Name="
				+ Name + ", Birth=" + Birth + ", Gender=" + Gender + "]";
	}

}
