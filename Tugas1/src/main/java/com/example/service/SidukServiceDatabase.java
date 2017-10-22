package com.example.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.SidukMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SidukServiceDatabase implements SidukService
{
    @Autowired
    private SidukMapper sidukMapper;

    @Override
	public PendudukModel selectPenduduk(String nik) {
		log.info("select penduduk with nik {}", nik);
		return sidukMapper.selectPenduduk(nik);
	}

	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		log.info("select keluarga with nkk {}", nkk);
		KeluargaModel keluarga = sidukMapper.selectKeluarga(nkk);
		KelurahanModel kelurahan = sidukMapper.selectKelurahan(keluarga.getId_kelurahan());
		keluarga.setNama_kelurahan(kelurahan.getNama_kelurahan());
		KecamatanModel kecamatan= sidukMapper.selectKecamatan(kelurahan.getId_kecamatan());
		keluarga.setNama_kecamatan(kecamatan.getNama_kecamatan());
		KotaModel kota = sidukMapper.selectKota(kecamatan.getId_kota());
		keluarga.setNama_kota(kota.getNama_kota());
		return keluarga;
	}
	
	@Override
	public void addPenduduk(PendudukModel penduduk) {
		String nikBaru = generateNik(penduduk);
		penduduk.setNik(nikBaru.substring(0,16));
		penduduk.setId(sidukMapper.getTotalPenduduk() + 1 + "");
		log.info(nikBaru);
		sidukMapper.addPenduduk(penduduk);
	}
	
	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		String nikBaru = generateNik(penduduk);
		penduduk.setNik_lama(penduduk.getNik());
		penduduk.setNik(nikBaru);
		log.info(nikBaru);
		sidukMapper.updatePenduduk(penduduk);
	}
	
	public String generateNik(PendudukModel penduduk) {
		log.info("NIK generated");
		String nik = "";
		String[] tglSplit = penduduk.getTanggal_lahir().split("-");
		nik += sidukMapper.selectKodeKecamatan(penduduk.getTempat_lahir());
		nik += (Integer.parseInt(tglSplit[2]) + (penduduk.getJenis_kelamin()*40)) + "" + tglSplit[1] + tglSplit[0].substring(2);
		System.out.println(nik);
		int pendudukMirip = sidukMapper.countPendudukMirip(nik).size();
		System.out.println(sidukMapper.countPendudukMirip(nik).size());
		int ujungNik = 1 + pendudukMirip;
		NumberFormat nf = new DecimalFormat("0000");
		nik+= nf.format(ujungNik);
//		if(ujungNik < 10 ){
//			nik += "000" + ujungNik;
//		} else if(ujungNik < 100 ){
//			nik += "00" + ujungNik;
//		} else if(ujungNik < 1000 ){
//			nik += "0" + ujungNik;
//		} else {
//			nik += ujungNik + "";
//		}
		
		System.out.println(nik);
		return nik;
	}
	
	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		String nkkBaru = generateNkk(keluarga);
		keluarga.setNomor_kk(nkkBaru.substring(0,16));
		keluarga.setId_kelurahan(sidukMapper.selectIdKelurahan(keluarga.getNama_kelurahan()));
		keluarga.setId(sidukMapper.getTotalKeluarga() + 1 + "");
		keluarga.setIs_tidak_berlaku("0");
		log.info(nkkBaru);
		sidukMapper.addKeluarga(keluarga);
	}
	
	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		String nkkBaru = generateNkk(keluarga);
		keluarga.setNomor_kk_lama(keluarga.getNomor_kk());
		keluarga.setNomor_kk(nkkBaru.substring(0,16));
		keluarga.setId_kelurahan(sidukMapper.selectIdKelurahan(keluarga.getNama_kelurahan()));
		keluarga.setIs_tidak_berlaku("0");
		log.info(nkkBaru);
		sidukMapper.updateKeluarga(keluarga);
	}
	
	public String generateNkk(KeluargaModel keluarga) {
		log.info("NKK generated");
		String nkk = "";
		String[] tglSplit = java.time.LocalDate.now().toString().split("-");
		nkk += sidukMapper.selectKodeKecamatan(keluarga.getNama_kecamatan()).substring(0,6);
		nkk += "" + tglSplit[2] + tglSplit[1] + tglSplit[0].substring(2);
		
		int keluargaMirip = sidukMapper.countKeluargaMirip(nkk);
	
		int ujungNkk = 1 + keluargaMirip;
		NumberFormat nf = new DecimalFormat("0000");
		nkk+= nf.format(ujungNkk);
//		if(ujungNkk < 10 ){
//			nkk += "000" + ujungNkk;
//		} else if(ujungNkk < 100 ){
//			nkk += "00" + ujungNkk;
//		} else if(ujungNkk < 1000 ){
//			nkk += "0" + ujungNkk;
//		} else {
//			nkk += ujungNkk + "";
//		}
		System.out.println(nkk);
		return nkk;
	}
	
	@Override
	public void setIsWafat(PendudukModel penduduk) {
		sidukMapper.setIsWafat(penduduk.getNik());
	}
}