package com.st.lmssql.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericDao<T> {
	public boolean add(T obj) throws SQLException;
	public T get(int objId) throws SQLException;
	public ArrayList<T> getAll() throws SQLException;
	public boolean update(T obj) throws SQLException;
	public boolean delete(T obj) throws SQLException;
	public boolean has(int objId) throws SQLException;
}