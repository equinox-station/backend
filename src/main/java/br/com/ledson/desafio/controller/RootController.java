package br.com.ledson.desafio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RootController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public RedirectView localRedirect() {
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/index.jsf");
		return redirectView;
	}
}