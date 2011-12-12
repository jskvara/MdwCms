package cms.model.rest;

import cms.model.rest.bean.Event;
import cms.model.rest.bean.EventPut;
import cms.model.rest.bean.Events;
import cms.model.rest.bean.Persons;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

public class RestClient {
	private String url;
	private Client client = Client.create();
	private WebResource webResource;

	public RestClient(String url) {
		this.url = url;
		webResource = client.resource(url);
	}

	public Event getEvent(String path) {
		JAXBElement<Event> e = webResource
				.path(path)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(new GenericType<JAXBElement<Event>>() {});

		return e.getValue();
	}

	public Events getEvents(String path) {
		JAXBElement<Events> e = webResource
				.path(path)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(new GenericType<JAXBElement<Events>>() {});

		return e.getValue();
	}

	public Persons getPersons(String path) {
		JAXBElement<Persons> e = webResource
				.path(path)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(new GenericType<JAXBElement<Persons>>() {});

		return e.getValue();
	}

	public String putEvent(String path, EventPut event) {
		ClientResponse response = webResource
				.path(path)
				.queryParams(event.getMap())
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.put(ClientResponse.class, event);
//		int status = response.getStatus();
		String textEntity = response.getEntity(String.class);

		return textEntity;
	}

	public String deleteEvent(String path) {
		ClientResponse response = webResource
				.path(path)
				.delete(ClientResponse.class);
		int status = response.getStatus();
		String textEntity = response.getEntity(String.class);

		return textEntity;
	}
}
