package biz.domain;

import biz.Bean;
import lombok.Data;

@Data
public class ExamBean implements Bean {
	private int id = 0; // 試験ID
	private int studentId = 0; // 学生ID
	private String subjectName = null; // 科目名
	private float point; // 点数

	public ExamBean(){
	}

	public ExamBean(int studentId, String subjectName, float point) {
		this.setStudentId(studentId);
		this.setSubjectName(subjectName);
		this.setPoint(point);
	}
}
