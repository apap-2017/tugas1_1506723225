package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.PendudukModel;
import com.example.service.SidukService;

@Controller
public class SidukController
{
    @Autowired
    SidukService sidukDAO;

    @RequestMapping("/")
    public String add ()
    {
        return "home";
    }
    
    @RequestMapping("/penduduk")
	public String viewPenduduk(Model model, 
			@RequestParam(value = "nik", required = false) String nik)
	{
		PendudukModel penduduk = sidukDAO.selectPenduduk(nik);
		
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "view-penduduk";
		} else {
			return "not-found";
		}
	}
    
    @RequestMapping("/keluarga")
	public String viewKeluarga(Model model,
			@RequestParam(value = "nkk", required = false) String nkk)
	{
		KeluargaModel keluarga = sidukDAO.selectKeluarga(nkk);
		
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			return "view-keluarga";
		} else {
			return "not-found";
		}
	}
    
    
    @RequestMapping("/penduduk/tambah")
    public String addPenduduk(Model model)
    {
    	return "form-tambah-penduduk";
    }
	
	@RequestMapping(value="/penduduk/tambah", method=RequestMethod.POST)
	public String tambahPenduduk(Model model, @ModelAttribute PendudukModel penduduk) {
		sidukDAO.addPenduduk(penduduk);
		
		model.addAttribute("penduduk", penduduk);
		return "sukses-tambah-penduduk";
	}
    
	@RequestMapping("/keluarga/tambah")
    public String addKeluarga(Model model)
    {
    	return "form-tambah-keluarga";
    }
	
	@RequestMapping(value="/keluarga/tambah", method=RequestMethod.POST)
	public String tambahKeluarga(Model model, @ModelAttribute KeluargaModel keluarga) {
		sidukDAO.addKeluarga(keluarga);
		
		model.addAttribute("keluarga", keluarga);
		return "sukses-tambah-keluarga";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}")
	public String updatePenduduk(Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = sidukDAO.selectPenduduk(nik);
		
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			return "form-ubah-penduduk";
		} else {
			return "not-found";
		}
	}
	
	@RequestMapping(value="/penduduk/ubah/{nik}", method=RequestMethod.POST)
	public String updatePenduduk(Model model, @ModelAttribute PendudukModel penduduk) {
		sidukDAO.updatePenduduk(penduduk);
		
		model.addAttribute("penduduk", penduduk);
		System.out.println(penduduk.getNik());
		return "sukses-ubah-penduduk";
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
    public String updateKeluarga(Model model, @PathVariable(value = "nkk") String nkk)
    {
		KeluargaModel keluarga = sidukDAO.selectKeluarga(nkk);
		
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			return "form-ubah-keluarga";
		} else {
			return "not-found";
		}
	}
	
	@RequestMapping(value="/keluarga/ubah/{nkk}", method=RequestMethod.POST)
	public String updateKeluarga(Model model, @ModelAttribute KeluargaModel keluarga) {
		sidukDAO.updateKeluarga(keluarga);
		
		model.addAttribute("keluarga", keluarga);
		return "sukses-ubah-keluarga";
	}
	
	@RequestMapping(value="/penduduk/mati/", method=RequestMethod.POST)
	public String setIsWafat(Model model, @ModelAttribute PendudukModel penduduk)
	{
		System.out.println(penduduk.getNik());
		sidukDAO.setIsWafat(penduduk);
		model.addAttribute("penduduk", penduduk);
		return "isWafat";
	}
}