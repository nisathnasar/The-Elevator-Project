package uk.ac.aston.coursework.elevator.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import uk.ac.aston.coursework.elevator.people.Person;

/**
 * This class implements Priority Queue using LinkedList. It is restricted so
 * that objects of type {@code Person} can enter and use. It uses a field called
 * queueNumber to remember the order of insertion and also, everyone can be
 * accessed and iterated through. The user should use {@code setQueueNumber()}
 * method to give a ticket number for each {@code Person}. Each type of
 * {@code Person} will implement {@code Comparable} to decide who has more
 * priority.
 * 
 * @author Nisath
 *
 */
public class PersonQueue implements Iterable<Person> {
	private List<Person> list;
	private int queueNumber = 1;

	public PersonQueue() {
		list = new LinkedList<Person>();
	}

	/**
	 * 
	 * @param person the person to be added to the back of the queue.
	 */
	public void enQueue(Person person) {
		if (person != null) {
			if (!inQueue(person)) {
				stabilizeQueueNumber();
				list.add(person);
				person.setQueueNumber(queueNumber);
				queueNumber++;
				Collections.sort(list);
			} else {
				throw new NullPointerException("Person is already in the queue.");
			}
		} else {
			throw new NullPointerException("Person enqueing is null");
		}
	}

	/**
	 * This method removes the person in front of the queue.
	 * 
	 * @return the person in the front of the queue.
	 */
	public Person deQueue() {
		Person p = null;
		if (list.size() > 0) {
			p = list.get(0);
			p.setQueueNumber(0);
			list.remove(0);
		}
		return p;
	}

	/**
	 * This method removes the person from the queue.
	 * 
	 * @param person the person to be removed from the queue.
	 */
	public void remove(Person person) {
		Iterator<Person> iter = iterator();
		while (iter.hasNext()) {
			if (iter.next().equals(person)) {
				iter.remove();
			}
		}
	}

	/**
	 * This method returns the person in front of the queue without removing them.
	 * 
	 * @return the person in front of the queue.
	 */
	public Person peek() {
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * This method checks if a person is already in the queue.
	 * 
	 * @param person the person to be checked if they are already in the queue.
	 * @return true if the {@code person} is already in the queue.
	 */
	public boolean inQueue(Person person) {
		return list.contains(person);
	}

	private void stabilizeQueueNumber() {
		if (list.size() == 0) {
			queueNumber = 1;
		}
	}

	/**
	 * 
	 * @return a list of type {@code List<Integer>} containing everyone in the queue
	 *         in order.
	 */
	public List<Person> getQueue() {
		return new ArrayList<Person>(list);
	}

	@Override
	public Iterator<Person> iterator() {
		return list.iterator();
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
