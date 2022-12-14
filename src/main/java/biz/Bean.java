package biz;

import java.io.Serializable;

// 各実装DAO（具象クラス）からのBeanへのアクセスを抽象化したいため、共通のインターフェイスを
// 登録しておきます。
public interface Bean extends Serializable {

}