package biz.domain;

import biz.Bean;
import lombok.Data;

/*
 * システムで使用するJava Beansを定義します。
 * JavaBeans（またはPOJO）である条件は次の3つです。
 * ①Serializableインターフェイスを実装していること（直列化できること）
 * 今回の場合「プロパティがすべてint等プリミティブかJava標準のクラスからの派生クラスであること」
 * と同値ですが、敢えてBeanインターフェイスを実装することでわかりやすく解決しています。
 * ②デフォルトコンストラクタがあること
 * 今回、StudentBeanおよびExamBeanでは外部からデフォルトコンストラクタでアクセスすることはないので、
 * private宣言することで隠ぺいしています。（つまり厳密にはBeansではなくなります汗）
 * ③プロパティへのアクセサメソッドが実装されている
 * 今回、setter/getterおよびisXXXといったアクセサメソッドを、「Lombok」を利用することで回避しています。
 * プロジェクトに「Lombok」ライブラリを登録し、Beanに@Dataアノテーションを追加することで、
 * いちいちアクセサメソッドを実装する煩わしさから解放されます(笑)
 * 注）Eclipseで開発する場合、Eclipse自身にも「Lombok」をインストールする必要があります。
*/
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

	private StudentBean() {
	}

	// IDを必要としないアクセスはこちら
	public StudentBean(String serial, String name, String furi, String birth,
			boolean isMale, String addr) {
		this(0, serial, name, furi, birth, isMale, addr);
	}

	// IDを必要とするアクセスはこちら
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

	// 学年とクラスはオプションです！(笑)
	public void register(int grade, String className) {
		this.setGrade(grade);
		this.setClassName(className);
	}

}
