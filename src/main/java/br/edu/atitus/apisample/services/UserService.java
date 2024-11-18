package br.edu.atitus.apisample.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

@Service
public class UserService {
	// Essa classe possui uma dependência de um objeto 
	private final UserRepository repository;
	
	public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}

	private static final String EMAIL_REGEX = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

	public UserEntity save(UserEntity newUser) throws Exception {
		// Regras de negócio - Validações e ajustes necessários conforme solicitação do cliente
		if (newUser == null)
			throw new Exception("Objeto nulo!");
		
		if (newUser.getName() == null || newUser.getName().isEmpty())
			throw new Exception("Nome inválido!");
		newUser.setName(newUser.getName().trim());
		
		if (newUser.getEmail() == null || newUser.getEmail().isEmpty())
			throw new Exception("Email inválido!");
	
		// Validar o email com regex
		if (!isValidEmail(newUser.getEmail()))
			throw new Exception("Email inválido!");
		newUser.setEmail(newUser.getEmail().trim());
		
		if (this.repository.existsByEmail(newUser.getEmail()))
			throw new Exception("Já existe usuário cadastrado com este e-mail!");
		
		if (newUser.getPassword() == null || newUser.getPassword().isEmpty())
			throw new Exception("Senha inválida!");
		
		// Invoca camada repository para persistência dos dados
		this.repository.save(newUser);
		
		return newUser;
	}

	public List<UserEntity> findAll() throws Exception {
		return repository.findAll();	}
}
