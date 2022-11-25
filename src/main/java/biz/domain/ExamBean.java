package biz.domain;

import biz.Bean;
import lombok.Data;

@Data
public class ExamBean implements Bean {
	private int id = 0; // 試験ID
	//private int studentId = 0; // 学生ID
	private StudentBean student = null; // 学生モデルを内包
	private String subjectName = null; // 科目名
	private float point; // 点数

	public ExamBean(){
	}

	public ExamBean(StudentBean student, String subjectName, float point) {
		this(0, student, subjectName, point);
	}

	public ExamBean(int id, StudentBean student, String subjectName, float point){
		this.setId(id);
		this.setStudent(student);
		this.setSubjectName(subjectName);
		this.setPoint(point);
	}
}
