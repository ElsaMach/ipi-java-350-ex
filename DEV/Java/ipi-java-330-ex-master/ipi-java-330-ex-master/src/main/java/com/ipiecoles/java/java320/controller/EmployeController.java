package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.model.Employe;
import com.ipiecoles.java.java320.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



import java.util.Map;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;


    //Afiicher un employe
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET)

    public String detail(@PathVariable(name = "id") Long id, Map<String, Object>model) {
        Employe employe = employeRepository.findOne(id);
        model.put("employe",employe);
        return "employes/detail";
    }

    //Recherche par matricule
    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            params = "matricule")

    public String findByMatricule(@RequestParam(name = "matricule") String matricule, Map<String, Object> model) {
        Employe employe = employeRepository.findMyMatricule(matricule);
        model.put("employe",employe);
        return "employes/detail";
    }



   @RequestMapping(
            value = "",
            method = RequestMethod.GET)

    public String findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "matricule") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            Map<String, Object>model){

        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<Employe> employes = employeRepository.findAll(pageRequest);

        model.put("employes", employes);
        model.put("pageAffichage",page + 1);
        model.put("start",page * size + 1);
        model.put("end",(page+1) * size);
        model.put("nextPage",page + 1);
        model.put("previousPage",page - 1);
        model.put("size",size);
        model.put("page",page);
        model.put("sortProperty",sortProperty);
        model.put("sortDirection",sortDirection);
        return "employes/liste";
    }


}
