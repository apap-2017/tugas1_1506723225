package com.example.model;
import java.math.BigInteger;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel
{
    private String id;
    private String nik;
    private String nama;
    private String tempat_lahir;
    private String tanggal_lahir;
    private int jenis_kelamin;
    private int is_wni;
    private String id_keluarga;
    private String agama;
    private String pekerjaan;
    private String status_perkawinan;
    private String status_dalam_keluarga;
    private String golongan_darah;
    private int is_wafat;
    private String alamat;
	private String rt;
	private String rw;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
	private List<KeluargaModel> keluarga;
	private String nik_lama;
}