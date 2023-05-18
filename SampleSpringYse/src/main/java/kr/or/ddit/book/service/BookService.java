package kr.or.ddit.book.service;

import java.util.List;
import java.util.Map;

public interface BookService {
	public String insertBook(Map<String, Object> map);

	public Map<String, Object> selectBook(Map<String, Object> map);

	public boolean updateBook(Map<String, Object> map);
	
	public boolean removeBook(Map<String, Object> map);

	public List<Map<String, Object>> selectBookList(Map<String, Object> map);
}
