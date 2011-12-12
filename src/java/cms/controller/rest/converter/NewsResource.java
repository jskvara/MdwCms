package cms.controller.rest.converter;

import cms.model.model.NewsEntity;
import cms.model.service.NewsService;
import cms.model.service.ServiceException;
import cms.util.GuiceUtil;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceException;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.sun.jersey.api.NotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

/**
 * Vehicle web service resource
 */
@Path("news")
public class NewsResource {

	@Context
	private UriInfo uriInfo;
//	@Context SecurityContext sc;//if (sc.isUserInRole("PreferredCustomer")
	private NewsService service = GuiceUtil.getService(NewsService.class);

	@GET
	@Produces({"application/json", "application/xml"})
	public NewsListRestConverter getAll() {
		List<NewsEntity> newslist = service.getAllNews();
		NewsListRestConverter newsListConverter =
				new NewsListRestConverter(newslist);

		return newsListConverter;
	}

	@GET
	@Path("/httpcache")
	public Response httpCache(@Context Request request) {
		Date lastModified = new Date(2011, 1, 1);
		Response.ResponseBuilder responseBuilder =
				request.evaluatePreconditions(lastModified);
		if (responseBuilder != null) {
			return responseBuilder.build();
		}
		String body = "aaa";
		return Response.ok(body).lastModified(lastModified).build();
	}

	@GET
	@Path("/sort")
	@Produces({"text/plain"})
	public String getSort(@QueryParam("size") @DefaultValue("100") String size) {
		int iSize = Integer.valueOf(size);
		double[] array = new double[iSize];
		Random rnd = new Random();
		for (int i = 0; i < iSize; i++) {
			array[i] = rnd.nextInt(100000);
		}
		Arrays.sort(array);

		return "sorted";
	}

	@GET
	@Path("/mem")
	@Produces({"application/json", "application/xml"})
	public NewsListRestConverter memcacheOperation() {
		String cacheKey = "result";
		NewsListRestConverter result = null;
		try {
			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			if (memcache.contains(cacheKey)) {
				result = (NewsListRestConverter) memcache.get(cacheKey);
				return result;
			}
		} catch (MemcacheServiceException e) {
			e.printStackTrace();
		}
		List<NewsEntity> newslist = service.getAllNews();
		result = new NewsListRestConverter(newslist);
		try {
			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			memcache.put(cacheKey, result);
		} catch (MemcacheServiceException e) {
			e.printStackTrace();
		}

		return result;
	}

	/*	@GET
	@Path("/rsi")
	@Produces({"text/plain"})
	public String remoteServiceInvocation() {
	RestModel rm = new RestModel();
	Persons persons = rm.getPersons();
	StringBuilder sb = new StringBuilder();
	for (Person p : persons.getPersons()) {
	sb.append(p.toString());
	}

	return sb.toString();
	}

	@GET
	@Path("/queue")
	@Produces("text/plain")
	public Response queue() {
	ResponseBuilder builder = Response.status(Response.Status.ACCEPTED);
	Queue queue = QueueFactory.getDefaultQueue();
	queue.add(TaskOptions.Builder.withUrl("/rest/news/test/").method(Method.GET));

	UriBuilder uri = uriInfo.getAbsolutePathBuilder();
	URI created = uri.path("test").build();
	builder.location(created);
	return builder.build();
	}*/
	@GET
	@Produces({"application/json", "application/xml"})
	@Path("{id}")
	public NewsRestConverter get(@PathParam("id") String id) {
		NewsEntity news = null;
		try {
			Key key = KeyFactory.stringToKey(id);
			news = service.getNews(key);
		} catch (IllegalArgumentException ignore) {
		}

		if (news == null) {
			throw new NotFoundException("Novinka \"" + id + "\" nebyla nalezena.");
		}

		return new NewsRestConverter(news);
	}

	@POST
	@Consumes({"application/json", "application/xml"})
	public Response add(JAXBElement<NewsRestConverter> newsInput) {
		Map<String, Object> newsMap = newsInput.getValue().getMap();
		//System.out.println("N: "+ newsMap);
		try {
			NewsEntity news = service.insert(newsMap);
			String key = KeyFactory.keyToString(news.getKey());
			URI vehicleUri = uriInfo.getAbsolutePathBuilder().path(key).build();
			return Response.created(vehicleUri).build();
		} catch (ServiceException ex) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
		}
	}

	@PUT
	@Consumes({"application/json", "application/xml"})
	public Response edit(JAXBElement<NewsRestConverter> newsInput) {
		Map<String, Object> newsMap = newsInput.getValue().getMap();
		//System.out.println("N:" + newsMap);
		if (newsMap.get("key") == null || newsMap.get("version") == null) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}

		try {
			NewsEntity news = service.edit(newsMap);
			String key = KeyFactory.keyToString(news.getKey());
			URI vehicleUri = uriInfo.getAbsolutePathBuilder().path(key).build();

			return Response.created(vehicleUri).build();
		} catch (ServiceException ex) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") String id,
			@DefaultValue("1") @QueryParam("version") Long version) {
		try {
			Key key = KeyFactory.stringToKey(id);
			if (service.getNews(key) == null) {
				throw new ServiceException("Novinka nebyla nalezena");
			}
			service.delete(key, version);
		} catch (ServiceException ex) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
		}

		return Response.ok().build();
	}
}
