package com.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/apis")
public class MyRest {
	
	@Path("async")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void asyncGet(final @Suspended AsyncResponse response) {
		new Thread() {
			public void run() {
				String result = callAppLogic();
				Response res = Response.ok().entity(formJsonResult(result)).build();
				response.resume(res);
			}
		}.start();
	}

	private String callAppLogic() {
		return new SocketClientHandler("127.0.0.1", 12345).connect();
	}
	
	private String formJsonResult(String result) {
		return "{\"AppResponse\":\""+result+"\"}";
	}
}
