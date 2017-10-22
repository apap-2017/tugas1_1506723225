package com.example.service;

import com.example.model.PendudukModel;

import java.util.List;

import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;

public interface SidukService {

	PendudukModel selectPenduduk(String nik);
	KeluargaModel selectKeluarga(String nkk);
	void addPenduduk(PendudukModel penduduk);
	void addKeluarga(KeluargaModel keluarga);
	void updatePenduduk(PendudukModel penduduk);
	void updateKeluarga(KeluargaModel keluarga);
	void setIsWafat(PendudukModel penduduk);

}
