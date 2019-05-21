package com.sms.springReactiveSandbox.web.predicates;

import java.net.URI;

import org.springframework.http.server.PathContainer;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import lombok.ToString;

@ToString()
public class CaseInsensitiveRequestPredicate implements RequestPredicate{
	private final RequestPredicate target;

	public CaseInsensitiveRequestPredicate(RequestPredicate target) {
		super();
		this.target = target;
	}

	@Override
	public boolean test(ServerRequest request) {
		return target.test(new LowerCaseUriServerRequestWrapper(request));
	}
	
}
class LowerCaseUriServerRequestWrapper extends ServerRequestWrapper{

	public LowerCaseUriServerRequestWrapper(ServerRequest delegate) {
		super(delegate);
	}

	@Override
	public URI uri() {
		return URI.create(super.uri().toString().toLowerCase());
	}

	@Override
	public String path() {
		return uri().getRawPath();
	}

	@Override
	public PathContainer pathContainer() {
		return PathContainer.parsePath(path());
	}
	
}