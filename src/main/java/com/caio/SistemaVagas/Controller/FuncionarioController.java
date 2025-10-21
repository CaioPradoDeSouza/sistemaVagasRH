package com.caio.SistemaVagas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caio.SistemaVagas.Model.Dependentes;
import com.caio.SistemaVagas.Model.Funcionario;
import com.caio.SistemaVagas.Repository.DependenteRepository;
import com.caio.SistemaVagas.Repository.FuncionarioRepository;

import jakarta.validation.Valid;

import com.caio.SistemaVagas.Model.Candidato;

@Controller
public class FuncionarioController {
	@Autowired
	private FuncionarioRepository fr;

	@Autowired
	private DependenteRepository dr;

	// chama o form de cadastrar Funcionario
	@RequestMapping("/cadastrarFuncionario")
	public String form() {
		return "funcionario/formFuncionario";
	}

	// cadastra funcionarios
	@RequestMapping(value = "cadastrarFuncionario", method = RequestMethod.POST)
	public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarFuncionario";
		}
		fr.save(funcionario);
		attributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
		return "redirect:/cadastrarFuncionario";

	}

	// listar funcionário
	@RequestMapping(value = "/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}

	// listar dependentes
	@RequestMapping("/dependentes/{id}")
	public ModelAndView dependentes(@PathVariable("id") long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/dependentes");
		mv.addObject("funcionarios", funcionario);

		// lista de dependentes baseada no funcionário
		Iterable<Dependentes> dependentes = dr.findByFuncionario(funcionario);
		mv.addObject("dependentes", dependentes);

		return mv;
	}

	// adicionar dependentes
	@RequestMapping(value = "/dependentes/{id}", method = RequestMethod.POST)
	public String dependentesPost(@PathVariable("id") long id, Dependentes dependentes, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagen", "Verifique os campos ! ");
			return "redirect:/dependentes/{id}";
		}

		if (dr.findByCpf(dependentes.getCpf()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "CPF duplicado");
			return "redirect:/dependentes/{id}";
		}

		Funcionario funcionario = fr.findById(id);
		dependentes.setFuncionario(funcionario);
		dr.save(dependentes);
		attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso ! ");

		return "redirect:/dependentes/{id}";

	}

	// deleta funcionario
	@RequestMapping(value = "/deletarFuncionario")
	public String deletarFuncionario(long id) {
		Funcionario funcionario = fr.findById(id);
		fr.delete(funcionario);
		return "redirect:/funcionarios";
	}

	// Métodos que atualizam funcionário
	// form
	@RequestMapping("editar-funcionario")
	public ModelAndView editarFuncionario(long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	// update funcionário
	@RequestMapping(value = "/editar-funcionario", method = RequestMethod.POST)
	public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result,
			RedirectAttributes attributes) {
		
		fr.save(funcionario);
		attributes.addFlashAttribute("success", "Funcionário alterado com sucesso !");
		
		long idLong = funcionario.getId();
		String id = "" + idLong;
		return "redirect:/dependentes/" + id;
	}
	
	// deletar dependente
	@RequestMapping(value = "/deletarDependente")
	public String deletarDependente(String cpf) {
		
		Dependentes dependente = dr.findByCpf(cpf);
		
		Funcionario funcionario = dependente.getFuncionario();
		String codigo = "" + funcionario.getId();
				
		dr.delete(dependente);
		
		return "redirect:/dependentes/" + codigo;
	}

}
