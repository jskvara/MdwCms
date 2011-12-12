package cms.model.rest.bean;

import cms.util.DateUtil;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.Date;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Event for put method
 */
@XmlRootElement
public class EventPut {
	private String name;
	private String description;
	private Date dateFrom;
	private Date dateTo;
	private String place;
	private Boolean isPublic;

	public MultivaluedMap getMap() {
		MultivaluedMap map = new MultivaluedMapImpl();
		map.add("name", name);
		map.add("description", description);
		map.add("place", place);
		map.add("dateFrom", getDateFrom());
		map.add("dateTo", getDateTo());
		map.add("isPublic", getIsPublic());

		return map;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsPublic() {
		return isPublic ? "true" : "false";
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
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

	public String getDateFrom() {
		return DateUtil.dateToStringEvent(dateFrom);
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return DateUtil.dateToStringEvent(dateTo);
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
}
