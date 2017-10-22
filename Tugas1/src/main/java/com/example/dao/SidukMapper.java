package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;

import com.example.model.PendudukModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;

@Mapper
public interface SidukMapper {

	@Select("select * from penduduk join keluarga join kelurahan join kecamatan join kota" + 
			" on penduduk.id_keluarga = keluarga.id and keluarga.id_kelurahan = kelurahan.id" + 
			" and kelurahan.id_kecamatan = kecamatan.id and kecamatan.id_kota = kota.id"
			+ " where nik = #{nik}")
    PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("SELECT * FROM kelurahan WHERE id = #{id}")
    KelurahanModel selectKelurahan (@Param("id") String id);
    
    @Select("SELECT * FROM kecamatan WHERE id = #{id}")
    KecamatanModel selectKecamatan (@Param("id") String id);
    
    @Select("SELECT * FROM kota WHERE id = #{id}")
    KotaModel selectKota (@Param("id") String id);
	
	@Select("select * from keluarga where nomor_kk = #{nkk}")
	@Results(value = {
	@Result(property="id", column = "id"),
	@Result(property="nomor_kk", column = "nomor_kk"),
	@Result(property="alamat", column = "alamat"),
	@Result(property="rt", column = "rt"),
	@Result(property="rt", column = "rt"),
	@Result(property="id_kelurahan", column = "id_kelurahan"),
	@Result(property="is_tidak_berlaku", column = "is_tidak_berlaku"),
	@Result(property="penduduk", column="id",
	javaType = List.class,
	many=@Many(select="selectAnggota"))
	
	})
	KeluargaModel selectKeluarga(@Param("nkk") String nkk);
	
	@Select("select * from penduduk where id_keluarga = #{id}")
	List<PendudukModel> selectAnggota (@Param("id") String id);
	
	@Select("select kode_kecamatan from kecamatan where nama_kecamatan = #{nama_kecamatan}")
	String selectKodeKecamatan(@Param("nama_kecamatan") String nama_kecamatan);
	
	@Select("select id from kelurahan where nama_kelurahan = #{nama_kelurahan}")
	String selectIdKelurahan(@Param("nama_kelurahan") String nama_kelurahan);
	
	@Select("select nik from penduduk where nik like '%" + "${nikMirip}" + "%'")
	List<String> countPendudukMirip(@Param("nikMirip") String nikMirip);
	
	@Select("SELECT COUNT(id) FROM penduduk")
	int getTotalPenduduk();
	
	@Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, "
			+ "jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, "
			+ "status_dalam_keluarga, golongan_darah, is_wafat) VALUES "
			+ "(#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, "
			+ "#{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, "
			+ "#{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void addPenduduk (PendudukModel penduduk);
	
	@Update("UPDATE penduduk SET nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} WHERE nik = #{nik_lama}")
    void updatePenduduk (PendudukModel penduduk);
    
    @Update("UPDATE penduduk SET is_wafat = 1 WHERE nik = #{nik}")
    void setIsWafat (@Param("nik") String nik);
	
    @Select("select COUNT(nomor_kk) from keluarga where nomor_kk like '%" + "${nkkMirip}" + "%'")
	int countKeluargaMirip(@Param("nkkMirip") String nkkMirip);
	
	@Select("SELECT COUNT(id) FROM keluarga")
	int getTotalKeluarga();
	
	@Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void addKeluarga (KeluargaModel keluarga);
	
	@Update("update keluarga set nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} where id = #{id}")
	void updateKeluarga(KeluargaModel keluarga);
	
}
