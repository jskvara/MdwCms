package cms.model.rest.bean;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Persons bean
 */
@XmlRootElement(name = "persons")
public class Persons {
	private List<Person> persons;

	public Persons() {
	}

	@XmlElement(name = "person")
	public List<Person> getPersons() {

		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Persons{persons=");
		for (Person p : persons) {
			sb.append(p);
		}
		sb.append('}');

		return sb.toString();
	}
}