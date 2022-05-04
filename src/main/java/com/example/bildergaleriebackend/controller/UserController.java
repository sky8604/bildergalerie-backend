package com.example.bildergaleriebackend.controller;

import com.example.bildergaleriebackend.entity.User;
import com.example.bildergaleriebackend.exception.EmailAlreadyExistsException;
import com.example.bildergaleriebackend.exception.WrongEmailException;
import com.example.bildergaleriebackend.exception.WrongPasswordException;
import com.example.bildergaleriebackend.model.*;
import com.example.bildergaleriebackend.service.JwtService;
import com.example.bildergaleriebackend.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("user")
public class UserController {

	@Inject
	private UserService userService;

	@Inject
	private JwtService jwtService;

	@POST
	@Path("signup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(@Valid User user) {
		try {
			user = userService.register(user.getEmail(), user.getUserName(), user.getPassword());
		} catch (EmailAlreadyExistsException ex) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(ex)
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(user)
				.build();
	}

	@POST
	@Path("signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(@Valid AuthRequestDTO userEmailPassword) {
		loginDTO loginDTO = new loginDTO();
		try {
			User user = userService.login(userEmailPassword.getEmail(), userEmailPassword.getPassword());
			loginDTO.setUserName(user.getUserName());
			loginDTO.setEmail(user.getEmail());
			loginDTO.setToken(jwtService.generateJwt(user.getEmail(), user.getUserName()));
		} catch (WrongEmailException ex) {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.entity(ex)
					.build();
		} catch (WrongPasswordException ex) {
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.entity(ex)
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(loginDTO)
				.build();
	}
}