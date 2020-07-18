package uk.ac.aston.coursework.elevator.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.aston.coursework.elevator.people.Person;

/**
 * Statistics takes Arrival Generator as argument and has methods to calculate
 * statistics with the information provided from Arrival Generator.
 * 
 * @author Nisath
 *
 */
public class Statistics {
	private ArrivalGenerator gen;
	private List<Person> eList, gList, mList, cList, mcList, totalList;
	private Map<Person, List<Integer>> map;

	/**
	 * Gets everyone from Arrival Generator and stores them in one list.
	 * 
	 * @param gen the ArrivalGenerator object from which, to obtain statistics from.
	 */
	public Statistics(ArrivalGenerator gen) {
		this.gen = gen;
		map = new HashMap<Person, List<Integer>>();

		addEveryoneToTotalList();
		addToMap();
	}

	private void addToMap() {
		for (int i = 0; i < totalList.size(); i++) {
			Person person = totalList.get(i);
			List<Integer> waitingList = totalList.get(i).getWaitingDataList();
			map.put(person, waitingList);
		}
	}

	private void addEveryoneToTotalList() {
		eList = gen.getEmployeeList();
		gList = gen.getGogglesList();
		mList = gen.getMugtomeList();
		cList = gen.getClientList();
		mcList = gen.getMaintenanceCrewList();
		totalList = new ArrayList<Person>();
		addToList(eList);
		addToList(gList);
		addToList(mList);
		addToList(cList);
		addToList(mcList);
	}

	private void addToList(List<Person> list) {
		for (Person n : list) {
			totalList.add(n);
		}
	}

	/**
	 * Calculates the averate waiting time in minutes.
	 * 
	 * @return returns average waiting time in minutes.
	 */
	public double getAverageWaitingTime() {
		List<Integer> waitingList = new ArrayList<Integer>();
		for (int i = 0; i < totalList.size(); i++) {
			Person person = totalList.get(i);
			for (int j = 0; j < person.getWaitingDataList().size(); j++) {
				waitingList.add(person.getWaitingDataList().get(j));
			}
		}
		if (waitingList.size() != 0) {
			int total = 0;
			for (int ticks : waitingList) {
				total += ticks;
			}
			return ((double) (total / waitingList.size())) * 10 / 60;
		}
		return 0;
	}

	/**
	 * Get a string of people with a list of thier waiting data.
	 * 
	 * @return a string people with a list of thier waiting data.
	 */
	public String getWaitingListMapString() {
		String str = "";
		for (Person p : map.keySet()) {
			str += p + ":   [";
			str += map.get(p) + "]";
			str += "\n";
		}
		return str;
	}
}
