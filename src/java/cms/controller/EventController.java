package cms.controller;

import cms.model.rest.RestModel;
import cms.model.rest.bean.Event;
import cms.model.rest.bean.EventPut;
import cms.model.rest.bean.Events;
import cms.model.rest.bean.Persons;
import cms.util.DateUtil;
import cms.util.Message;
import cms.util.Messages;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class EventController extends Controller {

	RestModel rm = new RestModel();

	public Navigation run() throws Exception {
		try {
			if (param("event") != null) {
				String eventParam = param("event");
				Event event = rm.getEvent(eventParam);
				request.setAttribute("event", event);
				return forward("/cms/event.jsp");
			} else if (param("addevent") != null) {
				String person = param("addevent");
				request.setAttribute("person", person);
				return forward("/cms/addEvent.jsp");
			} else if (param("submit") != null) {
				try {
					EventPut e = new EventPut();
					String person = asString("person");
					e.setName(asString("name"));
					e.setDescription(asString("description"));
					e.setPlace(asString("place"));
					Date dateFrom = DateUtil.stringToDateEvent(param("dateFrom"));
					e.setDateFrom(dateFrom);
					Date dateTo = DateUtil.stringToDateEvent(param("dateTo"));
					e.setDateTo(dateTo);
					Boolean pub = "on".equals(param("public")) ? true : false;
					e.setIsPublic(pub);
//					System.out.println("E: "+ e);
//					ObjectMapper mapper = new ObjectMapper();
//					mapper.writeValue(System.out, e);
					String ret = rm.putEvent(person, e);
					if (!ret.contains("success")) {
						Messages.setRequestMessage("Nepodařilo se vložit událost.",
								Message.ERROR);
						return forward("/cms/addEvent.jsp");
					}
					Messages.setSessionMessage("Událost byla vytvořena.");

					return redirect("/event?person=" + person);
				} catch (ParseException ex) {
					Messages.setRequestMessage("Chybný formát data dd.mm.YYYY HH:mm",
							Message.ERROR);
					return forward("/cms/addEvent.jsp");
				}
			} else if (param("delete") != null) {
				String key = param("delete");
				String person = param("person");
				String ret = rm.deleteEvent(key);
				if (!ret.contains("success")) {
					Messages.setSessionMessage("Nepodařilo se smazat událost.",
							Message.ERROR);
				} else {
					Messages.setSessionMessage("Událost byla smazána.");
				}
				return redirect("event?person="+ person);
			} else if (param("person") != null) {// must be last !!!
				String person = param("person");
				List<Event> ev = null;
				try {
					Events events = rm.getEvents(person);
					ev = events.getEvents();
				} catch (UniformInterfaceException ex) {
					ex.printStackTrace();
				}
				request.setAttribute("person", person);
				request.setAttribute("events", ev);
				return forward("/cms/events.jsp");
			}

			Persons persons = rm.getPersons();
			request.setAttribute("persons", persons.getPersons());
			return forward("/cms/eventPersons.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			out.println("	<title>Events</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("	<h1>Events</h1>");
			out.println("	<p>Sluzba je nedostupna.</p>");
			out.println("</body>");
			out.println("</html>");
			return null;
		}
	}
}
