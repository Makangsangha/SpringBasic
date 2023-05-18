package kr.or.ddit.book.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.book.dao.BookDAO;

/*
 * 
 * 일반적으로 서비스 레이어는 인터페이스와 클래스를 함께 사용한다.
 * 스프링은 직접 클래스를 생성하는 것을 지향하고 인터페이스를 통해 접근하는 것을 권장하는 프레임워크이다.
 * 
 */

@Service
public class BookServiceImpl implements BookService{

	/*
	 *	Service 클래스는 비즈니스 클래스가 위치하는 곳이다
	 *	스프링 MVC 구조에서 서비스 클래스는 컨트롤러와 DAO를 연결하는 역할을 한다.
	 *  @Service는 스픵에 서비스 클래스임을 알려준다.
	 *  
	 *  데이터베이스 접근을 위해 BookDao 인스턴스를 주입받는다
	 *  클래스의 이름이 Impl로 끝나는 것은 implements의 약자로 관습에 따른다
	 *  Impl이 붙고 안붙고에 따라 클래스인지 인터페이스인지 구별하기 쉽다.
	 */
	@Inject
	private BookDAO bookDao;
	
	

	/**
	 *	
	 *	<p>책 등록</p>
	 *	@since SampleSpringYse 1.0
	 *	@author ddit_june
	 *	@param map 등록할 색 데이터
	 *	@return 성공 책ID, 실패 null
	 */
	@Override
	public String insertBook(Map<String, Object> map) {
		// affectRowCount 변수에는 영향받은 행 수가 담긴다.
		// insertBook 구문은 입력이 성공하면 1, 실패하면 0을 리턴한다.
		
		int affectRowCount = bookDao.insertBook(map);
		if(affectRowCount == 1) {
			// 결과가 성공일 시, map 인스턴스에 book 테이블의 pk인 book_id가 담겨져온다.
			return map.get("book_id").toString();
		}
		return null;
	}
	
	/**
	 * <p>책 상세보기</p>
	 * @since SampleSpringYse 1.0
	 * @author ddit_june
	 * @param map 색ID
	 * @return ID에 해당하는 책 정보 
	 */
	@Override
	public Map<String, Object> selectBook(Map<String, Object> map) {
		// 서비스 내 detail함수는 dao를 호출한 결과를 바로 리턴하는 일만 한다.
		return bookDao.selectBook(map);
	}

	/**
	 * <p>책 수정</p>
	 * @since SampleSpringYse 1.0
	 * @author ddit_june
	 * @param map 색 ID
	 * @return 성공 1, 실패 0
	 */
	@Override
	public boolean updateBook(Map<String, Object> map) {
		int affectRowCount = bookDao.updateBook(map);
		return affectRowCount == 1;
	}

	/**
	 * <p>책 삭제</p>
	 * @since SampleSpringYse 1.0
	 * @author ddit_june
	 * @param map 책 ID
	 * @return 성공 1, 실패 0
	 */
	@Override
	public boolean removeBook(Map<String, Object> map) {
		int affectRowCount = bookDao.removeBook(map);
		return affectRowCount == 1;
	}

	/**
	 * <p>책 목록</p>
	 * @since SampleSpringYse 1.0
	 * @author ddit_june
	 * @param map 책 키워드
	 * @return 성공 리스트(책들), 실패 null
	 */
	@Override
	public List<Map<String, Object>> selectBookList(Map<String, Object> map) {
		return bookDao.selectBookList(map);
	}
	
}
