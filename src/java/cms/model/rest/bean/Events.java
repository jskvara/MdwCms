package cms.model.rest.bean;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "events")
public class Events {
    private List<Event> events = new LinkedList<Event>();

    public Events() {
    }

	@XmlElement(name = "event")
	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Events{events=");
		for (Event e : events) {
			sb.append(e);
		}
		sb.append('}');

		return sb.toString();
	}
}
