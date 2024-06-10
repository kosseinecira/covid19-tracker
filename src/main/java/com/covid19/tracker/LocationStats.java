package com.covid19.tracker;


public class LocationStats {
	private String country;
	private String state;
	private String totalCasesToday;
	private int newCases;
	public int getNewCases() {
		return newCases;
	}
	public void setNewCases(int newCases) {
		this.newCases = newCases;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTotalCasesToday() {
		return totalCasesToday;
	}
	public void setTotalCasesToday(String totalCasesToday) {
		this.totalCasesToday = totalCasesToday;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "LocationStats [country=" + country + ", totalCasesToday=" + totalCasesToday + "]";
	}
	
	
}
