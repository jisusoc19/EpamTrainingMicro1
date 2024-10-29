package com.task3.service.trainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task3.Entity.Trainee;
import com.task3.Entity.Trainer;
import com.task3.Entity.User;
import com.task3.Exception.RestHandlerException.ResouceNotFoundException;
import com.task3.Repository.iTrainerdao;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class iTrainerServiceImpl implements iTrainerService {


	public iTrainerServiceImpl(iTrainerdao itrainerdaoJPA) {
		this.itrainerdaoJPA = itrainerdaoJPA;
	}

	public final iTrainerdao itrainerdaoJPA;
	

	@Override
	public Trainer save(Trainer trainer) {
		if(trainer==null || trainer.getUserid().getIsActive()==null) {
			log.error("Error en trainer save");
			throw new ResouceNotFoundException("Error al guardar" );
		} else {
			itrainerdaoJPA.save(trainer);
			log.info("trainer guardado con exito");
			
		}
		return trainer;
	}
	@Transactional(readOnly = true)
	@Override
	public Trainer findById(Long id) {
		if(itrainerdaoJPA.findById(id) == null || itrainerdaoJPA.findById(id).isEmpty()) {
			log.error("Trainer con id: " + id + "No encontrado");
			throw new ResouceNotFoundException("cliente", "id" ,id);
		}else {
			log.info("Trainer encontrado con id: " + id + " Encontrado " );
			return itrainerdaoJPA.findById(id).orElse(null);
		}
		
	}

	@Override
	public Trainer update(Trainer trainer, Long id) {
		Trainer Aactual = itrainerdaoJPA.findById(id).orElse(null);
		Trainer nuevotra=null;
	    if (!itrainerdaoJPA.findById(id).isPresent()) {
	        // Update the trainer
	    	log.error("error en Trainer update" );
	    	throw new ResouceNotFoundException("Trainer with ID " + id + " no update in the database");
	    }else {
	    	Aactual.setTrainertype(trainer.getTrainertype());
	    	nuevotra =itrainerdaoJPA.save(Aactual);
	    	log.info("Trainer actualizado");
	    	return nuevotra;
	    }
	
	}
	
	    
	

	
	@Transactional(readOnly = true)
	@Override
	public List<Trainer> findAll() {
		List<Trainer> lista = (List<Trainer>) itrainerdaoJPA.findAll();
		if(lista==null || lista.isEmpty()) {
			log.error("Lista de Trainer no encontrada" );
			throw new ResouceNotFoundException("clientes");
		}
		log.info("Lista de Trainer encontrada" );
		return (List<Trainer>) itrainerdaoJPA.findAll();
	}




	@Override
	public Trainer findbyusername(String username) {
		Trainer trainer =itrainerdaoJPA.findByUsername(username).orElse(null);
		if (itrainerdaoJPA.findByUsername(username).isPresent()) {
			log.info("Trainer encontrado con el username:" + username );
			return trainer;

		}else {
			log.error("Trainer no encontrado con el username:" + username );
			throw new ResouceNotFoundException();
		}

	}

	@Override
	public Boolean loggin(String username, String Password) {
		Trainer traineeE = itrainerdaoJPA.findByUsername(username).orElse(null);
		if(itrainerdaoJPA.findByUsername(username).isPresent()) {
			if(traineeE.getUserid().getPassword().equals(Password));
			log.info("Trainer iniciado sesion con el username:" + username );
			return true;
		}
		else {
			log.error("Error con el iniciado de sesion del Trainer con el username:" + username );
			return false;
		}

	}


	@Override
	public Trainer activeTranierTrainee(String username, Boolean status) {

		Trainer trainer = itrainerdaoJPA.findByUsername(username).orElse(null);
		if(itrainerdaoJPA.findByUsername(username).isPresent()) {
			trainer.getUserid().setIsActive(status);
			itrainerdaoJPA.save(trainer);
			log.info("Trainer con el username:" + username + " Status cambiado" );
		}else {
			log.error("Trainer con el username:" + username + " no se pudo cambiar el Status" );
			throw new ResouceNotFoundException();
		}

		return trainer;

	}


}