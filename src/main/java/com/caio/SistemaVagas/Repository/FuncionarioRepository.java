package com.caio.SistemaVagas.Repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.caio.SistemaVagas.Model.Funcionario;

public interface FuncionarioRepository extends CrudRepository <Funcionario, Long> {
	
	Funcionario findById(long id);
	
	//busca
	Funcionario findByNome(String nome);
	
	
	
}
