package com.covid19.tracker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

@Service
public class CoronaVirusDataService {

	private static String virusDataUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private static String virusDataurl2 = "https://raw.githubusercontent.com/datasets/covid-19/main/data/countries-aggregated.csv";
	private List<LocationStats> locationStatsList = new ArrayList<>();

	public List<LocationStats> getLocationStatsList() {
		return locationStatsList;
	}

	public void setLocationStatsList(List<LocationStats> locationStatsList) {
		this.locationStatsList = locationStatsList;
	}

	private Date date = Calendar.getInstance().getTime();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	private String strDate = dateFormat.format(date);

	@PostConstruct //  s m h d m y // seconds,minuts,hours,days,months,years
	@Scheduled(cron = "* * 1 * * *") /* Scheduling the run of a method on a regular basis */
	// making http call using http client
	public void fetchVirusData() throws IOException, InterruptedException {
		// creating new client
		HttpClient client = HttpClient.newHttpClient();
		// Creating a new request
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(virusDataUrl)).build();
		// sending request to the client
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		// System.out.println(response.body());
		// record is the column
		StringReader reader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
		for (CSVRecord record : records) {
			LocationStats locationStat = new LocationStats();
			String country = record.get("Country/Region");
			String state = record.get("Province/State");
			String totalCasesToday = record.get(record.size()-1);
			String totalCasesYesterday = record.get(record.size()-2);
			int newCasestoday = Integer.parseInt(totalCasesToday)-Integer.parseInt(totalCasesYesterday);
			locationStat.setNewCases(newCasestoday);
			locationStat.setCountry(country);
			locationStat.setState(state);
			locationStat.setTotalCasesToday(totalCasesToday);
			locationStatsList.add(locationStat);
			System.out.println(locationStat);
		}

	}
}
