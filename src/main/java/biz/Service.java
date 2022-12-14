package biz;

import java.util.List;

// 各DAOで実装（を強制）させたい基本機能を、Serviceインターフェイスとして定義します。
// ※入出力に使用する要素を基底クラス（インターフェイス）で定義していることに注意してください。
// ※なお、実装DAOはファウラー先生に倣い「Service」で統一します(笑)
public interface Service {
	public abstract List<Bean> findAll();
	public abstract Bean findById(int id) throws DataNotFoundException;
	public abstract boolean register(Bean bean);
	public abstract void update(Bean bean) throws DataNotFoundException;
	public abstract void delete(int id) throws DataNotFoundException;
}
