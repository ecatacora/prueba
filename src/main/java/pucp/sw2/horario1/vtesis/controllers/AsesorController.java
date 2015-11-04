/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucp.sw2.horario1.vtesis.controllers;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pucp.sw2.horario1.vtesis.dao.AsesorDao;
import pucp.sw2.horario1.vtesis.dao.PersonaDAO;

/**
 *
 * @author josesuk
 */

@Controller(value = "asesorController")

public class AsesorController {
    
    @Autowired
    AsesorDao asesorDao;
    
    
    /* se configura un log para este controlador*/
    private static final Logger log = Logger.getLogger("asesorController");
    
    
    @RequestMapping(value = "asesor/profile")
    public String profileAsesor(Model model){
        
        return "asesor/profile";
    }
    
    @RequestMapping(value = "asesor/lista_alumnos")
    public String listarAlumnos(Model model){
        
        model.addAttribute("lstAlumnos",asesorDao.listarAlumnos());
        return "asesor/lista_alumnos";
    }
    
    
    
    
    
    
    
    
    
}
