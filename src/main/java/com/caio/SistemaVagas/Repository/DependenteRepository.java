package com.caio.SistemaVagas.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.caio.SistemaVagas.Model.Dependente;
import com.caio.SistemaVagas.Model.Funcionario;

public interface DependenteRepository extends CrudRepository <Dependente, String> {
	
	Iterable<Dependente> findByFuncionario(Funcionario funcionario);
	
	
	// pensando no metodo delele
	Dependente findByCpf(String cpf);
	Dependente findById(long id);
	
	// criado para impelementar 
	List<Dependente> findByNome(String nome);
	
	@Query(value="select u from Dependente u where u.nome like %?1% ")
	List<Dependente>findByNomesDependentes(String nome);

}
