package br.edu.atitus.apisample.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.apisample.dtos.SignupDTO;
import br.edu.atitus.apisample.entities.TypeUser;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	// A classe AuthController possui dependência de UserService
	private final UserService service;
	
	
	// Injeção de dependência via método construtor
	public AuthController(UserService service) {
		super();
		this.service = service;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserEntity> createNewUser(@RequestBody SignupDTO signup) throws Exception{
		// Converter DTO em Entity (UserEntity)
		UserEntity newUser = new UserEntity();
		BeanUtils.copyProperties(signup, newUser);
		newUser.setType(TypeUser.Common);
		
		// Invocar a camada Service para salvar o novo User
		service.save(newUser);
		
		// Retornar o novo usuário salvo
		return ResponseEntity.ok(newUser);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handlerMethod(Exception ex){
		String msg = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(msg);
	}
	
}
