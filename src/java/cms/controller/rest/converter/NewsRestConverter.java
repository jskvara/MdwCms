package cms.controller.rest.converter;

import com.google.appengine.api.datastore.Key;
import cms.model.model.NewsEntity;
import cms.util.DateUtil;
import com.google.appengine.api.datastore.KeyFactory;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Alex
 */
@XmlRootElement(name = "news")
@XmlType(propOrder = {"key", "version", "title", "text", "created", "visible"})
public class NewsRestConverter implements IRestConverter {

	private NewsEntity entity;
	private Map<String, Object> map = new HashMap<String, Object>();

	public NewsRestConverter() {
		this.entity = new NewsEntity();
	}

	public NewsRestConverter(NewsEntity entity) {
		this.entity = entity;
	}

	public NewsEntity getEntity() {
		return entity;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	@XmlElement
	public String getKey() {
		return KeyFactory.keyToString(entity.getKey());
	}

	public void setKey(String key) {
		Key k = KeyFactory.stringToKey(key);
		map.put("key", k);
		this.entity.setKey(k);
	}

	@XmlElement
	public Long getVersion() {
		return entity.getVersion();
	}

	public void setVersion(Long version) {
		map.put("version", version);
		this.entity.setVersion(version);
	}

	@XmlElement
	public String getTitle() {
		return entity.getTitle();
	}

	public void setTitle(String title) {
		map.put("title", title);
		this.entity.setTitle(title);
	}

	@XmlElement
	public String getText() {
		return entity.getText();
	}

	public void setText(String text) {
		map.put("text", text);
		this.entity.setText(text);
	}

	@XmlElement
	public String getCreated() {
		return DateUtil.dateToString(entity.getCreated());
	}

	public void setCreated(String created) throws ParseException {
		map.put("created", created);
		this.entity.setCreated(DateUtil.stringToDate(created));
	}

	@XmlElement
	public Boolean getVisible() {
		return entity.getVisible();
	}

	public void setVisible(Boolean visible) {
		map.put("visible", visible);
		this.entity.setVisible(visible);
	}
}
