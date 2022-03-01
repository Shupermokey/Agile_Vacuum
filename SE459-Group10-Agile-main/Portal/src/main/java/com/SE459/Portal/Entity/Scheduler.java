package com.SE459.Portal.Entity;

import java.util.List;

import javax.persistence.*;

@Entity
public class Scheduler {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sid;

	public boolean M;
	public boolean T;
	public boolean W;
	public boolean Th;
	public boolean F;
	public boolean S;
	public boolean Sun;
	public String appt;
	public String freq;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public boolean isM() {
		return M;
	}
	public void setM(boolean m) {
		M = m;
	}
	public boolean isT() {
		return T;
	}
	public void setT(boolean t) {
		T = t;
	}
	public boolean isW() {
		return W;
	}
	public void setW(boolean w) {
		W = w;
	}
	public boolean isTh() {
		return Th;
	}
	public void setTh(boolean th) {
		Th = th;
	}
	public boolean isF() {
		return F;
	}
	public void setF(boolean f) {
		F = f;
	}
	public boolean isS() {
		return S;
	}
	public void setS(boolean s) {
		S = s;
	}
	public boolean isSun() {
		return Sun;
	}
	public void setSun(boolean sun) {
		Sun = sun;
	}
	public void setAppt(String appt) {
		this.appt = appt;
	}
	public String getAppt(String appt) {
		return appt;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getFreq(String freq) {
		return freq;
	}
	@Override
	public String toString() {
		return "Scheduler [sid=" + sid + ", M=" + M + ", T=" + T + ", W=" + W + ", Th=" + Th + ", F=" + F + ", S=" + S
				+ ", Sun=" + Sun + ", appt=" + appt + ", freq=" + freq + "]";
	}

	public String toCronString() {
		// String cronString = "0 ";
		String cronString = "0,10,20,30,40,50 "; //for testing
		if(appt.length() != 0) {
			String[] time = this.appt.split(":");
			cronString += time[1] + " " + time[0] + " * * ";
		}
		else {
			cronString += "0 12 * * "; // defaults to noon if no time specified
		}

		String weekDays = "";
		if(M) weekDays += "MON,";
		if(T) weekDays += "TUE,";
		if(W) weekDays += "WED,";
		if(Th) weekDays += "THU,";
		if(F) weekDays += "FRI,";
		if(S) weekDays += "SAT,";
		if(Sun) weekDays += "SUN,";
		weekDays = weekDays.substring(0, weekDays.length() - 1);

		return cronString + weekDays;
	}
	
	
	
}
