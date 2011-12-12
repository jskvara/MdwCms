package cms.model.rest.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Event person bean
 */
@XmlRootElement
public class Person {
	private String user;
	private Boolean admin;

	public Person() {
	}

	public Boolean getAdmin() {
		return admin;
	}

	public String getAdminString() {
		return admin ? "ano" : "ne";
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}