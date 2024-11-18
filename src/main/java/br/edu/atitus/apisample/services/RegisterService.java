package br.edu.atitus.apisample.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.repositories.RegisterRepository;

@Service
public class RegisterService {

	private final RegisterRepository repository;

	public RegisterService(RegisterRepository repository) {
		super();
		this.repository = repository;
	}
	
	// save
	public RegisterEntity save(RegisterEntity newRegister) throws Exception {
		// Validações de regra de negócio
		if (newRegister.getUser() == null || newRegister.getUser().getId() == null)
			throw new Exception("Usuário inválido!");
		
		if (newRegister.getLatitude() < -90 || newRegister.getLatitude() > 90)
			throw new Exception("Latitude inválida!");
		
		if (newRegister.getLongitude() < -180 || newRegister.getLongitude() > 180)
			throw new Exception("Longitude inválida!");
		
		// Invocar método save da camada repository
		repository.save(newRegister);
		
		// Retornar o objeto salvo
		return newRegister;
	}
	
	// findAll
	public List<RegisterEntity> findAll() throws Exception {
		List<RegisterEntity> registers = repository.findAll();
		return registers;
	}
	
	// findById
	public RegisterEntity findById(UUID id) throws Exception {
		RegisterEntity register = repository.findById(id).orElseThrow(() -> new Exception("Registro não encontrado com este id!"));
		return register;
	}
	
	// deleteById
	public void deleteById(UUID id) throws Exception {
		this.findById(id);
		repository.deleteById(id);
	}
}
