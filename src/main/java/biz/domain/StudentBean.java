package biz.domain;

import biz.Bean;
import lombok.Data;

@Data
public class StudentBean implements Bean {
	private int id = 0; // 学生ID
	private int grade = 0; // 学年
	private String className = null; // クラス
	private String serial = null; // 学生番号（'YY-SSXXX'）
	private String name = null; // 名前
	private String furi = null; // フリガナ
	private String birth = null; // 生年月日（8桁）
	private boolean isMALE = true; // 性別（男性：TRUE）
	private String address = null; // 住所

	public StudentBean() {
	}

	public StudentBean(String serial, String name, String furi, String birth,
			boolean isMale, String addr) {
		this(0, serial, name, furi, birth, isMale, addr);
	}

	public StudentBean(int id, String serial, String name, String furi, String birth,
			boolean isMale, String addr) {
		this.setId(id);
		this.setSerial(serial);
		this.setName(name);
		this.setFuri(furi);
		this.setBirth(birth);
		this.setMALE(isMale);
		this.setAddress(addr);
	}

	public void register(int grade, String className) {
		this.setGrade(grade);
		this.setClassName(className);
	}

}
