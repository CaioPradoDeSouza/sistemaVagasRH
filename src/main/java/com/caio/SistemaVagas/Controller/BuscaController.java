package com.caio.SistemaVagas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.caio.SistemaVagas.Model.Candidato;
import com.caio.SistemaVagas.Model.Dependentes;
import com.caio.SistemaVagas.Model.Funcionario;
import com.caio.SistemaVagas.Model.Vaga;

import com.caio.SistemaVagas.Repository.FuncionarioRepository;
import com.caio.SistemaVagas.Repository.DependenteRepository;
import com.caio.SistemaVagas.Repository.VagaRepository;
import com.caio.SistemaVagas.Repository.CandidatoRepository;


@Controller
public class BuscaController {
	
	@Autowired
	private FuncionarioRepository fr;
	
	@Autowired
	private VagaRepository vr;
	
	@Autowired
	private DependenteRepository dr;
	
	@Autowired
	private CandidatoRepository cr;
	
	//GET
	@RequestMapping("/")
	public ModelAndView abrirIndex() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	
	//POST
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView buscarIndex(@RequestParam("buscar") String buscar, @RequestParam("nome") String nome) {
		
		ModelAndView mv = new ModelAndView("index");
		String mensagem = "Resultado da busca por " + buscar;
		
		if(nome.equals("nomeFuncionario")) {
			mv.addObject("funcionarios", fr.findByNomes(buscar));
			
		}else if(nome.equals("nomeDependente")) {
			mv.addObject("dependentes", dr.findByNomesDependentes(buscar));
			
		}else if(nome.equals("nomeCandidato")) {
			mv.addObject("candidatos", cr.findByNomesCandidatos(buscar));
			
		}else if(nome.equals("tituloVaga")) {
			mv.addObject("vagas", vr.findByNomesVaga(nome));
			
		}else {
			mv.addObject("funcionarios", fr.findByNomes(buscar));
			mv.addObject("dependentes", dr.findByNomesDependentes(buscar));
			mv.addObject("candidatos", cr.findByNomesCandidatos(buscar));
			mv.addObject("vagas", vr.findByNomesVaga(nome));
			
		}
		
		mv.addObject("mensagem", mensagem);
		
		return mv;
		
	}
	
	
	
}
