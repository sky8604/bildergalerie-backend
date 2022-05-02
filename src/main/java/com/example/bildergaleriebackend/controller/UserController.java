package com.example.bildergaleriebackend.controller;

import com.example.bildergaleriebackend.entity.User;
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
		user = userService.register(user.getEmail(), user.getUserName(), user.getPassword());

		return Response
				.status(200)
				.entity(user)
				.build();
	}

	@POST
	@Path("signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(@Valid AuthRequestDTO userEmailPassword) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			User user = userService.login(userEmailPassword.getEmail(), userEmailPassword.getPassword());
			responseDTO.setToken(jwtService.generateJwt(user.getEmail(), user.getUserName()));
		} catch (WrongEmailException e) {
			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("wrong_email");
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.entity(errorResponseDTO)
					.build();
		} catch (WrongPasswordException e) {
			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("wrong_password");
			return Response
					.status(Response.Status.UNAUTHORIZED)
					.entity(errorResponseDTO)
					.build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(responseDTO)
				.build();
	}
}