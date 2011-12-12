package cms.model.rest;

import cms.model.rest.bean.Event;
import cms.model.rest.bean.EventPut;
import cms.model.rest.bean.Events;
import cms.model.rest.bean.Persons;

/**
 * RestModel
 */
public class RestModel {
	private static final String URL = "http://mdw-calendar.appspot.com/rest/";
	private RestClient rc;

	public RestModel() {
		rc = new RestClient(URL);
	}

	public Persons getPersons() {
		String path = "users";
		Persons p = rc.getPersons(path);

		return p;
	}

	public Events getEvents(String user) {
		String path = "events/user/".concat(user);
		Events e = rc.getEvents(path);

		return e;
	}

	public Event getEvent(String key) {
		String path = "events/".concat(key);
		Event e = rc.getEvent(path);

		return e;
	}

	public String putEvent(String person, EventPut event) {
		String path = "events/".concat(person);
		String ret = rc.putEvent(path, event);

		return ret;
	}

	public String deleteEvent(String key) {
		String path = "events/".concat(key);
		String ret = rc.deleteEvent(path);

		return ret;
	}
}
