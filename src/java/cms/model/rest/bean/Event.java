package cms.model.rest.bean;

import cms.util.DateUtil;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Event converter from/to JSON
 */
@XmlRootElement
public class Event {
	private String author;
	private Date dateFrom;
	private Date dateTo;
	private String description;
	private String name;
	private String place;
	private Boolean pub;
	private String stringKey;

	public Event() {
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public String getDateFromString() {
		return DateUtil.dateToStringEvent(dateFrom);
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public String getDateToString() {
		return DateUtil.dateToStringEvent(dateTo);
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getPublic() {
		return pub;
	}

	public String getPublicString() {
		return pub ? "ano" : "ne";
	}

	public void setPublic(Boolean pub) {
		this.pub = pub;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStringKey() {
		return stringKey;
	}

	public void setStringKey(String stringKey) {
		this.stringKey = stringKey;
	}

	@Override
	public String toString() {
		return "Event{" + "author=" + author + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", description=" + description + ", name=" + name + ", place=" + place + ", pub=" + pub + ", stringKey=" + stringKey + '}';
	}
}
