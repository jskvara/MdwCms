package cms.controller.rest.converter;

import cms.model.model.NewsEntity;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alex
 */
@XmlRootElement(name = "newslist")
public class NewsListRestConverter implements IRestConverter, Serializable {

    private List<NewsRestConverter> entities = new LinkedList<NewsRestConverter>();

    public NewsListRestConverter() {
    }

    public NewsListRestConverter(List<NewsEntity> newsEntities) {
        for (NewsEntity ne : newsEntities) {
            entities.add(new NewsRestConverter(ne));
        }
    }

    @XmlElement(name = "news")
    public List<NewsRestConverter> getEntities() {
        return entities;
    }
}
