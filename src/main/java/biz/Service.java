package biz;

import java.util.List;

public interface Service {
	public abstract List<Bean> findAll();
	public abstract Bean findById(int id) throws DataNotFoundException;
	public abstract boolean register(Bean bean);
	public abstract void update(Bean bean) throws DataNotFoundException;
	public abstract void delete(int id) throws DataNotFoundException;
}
