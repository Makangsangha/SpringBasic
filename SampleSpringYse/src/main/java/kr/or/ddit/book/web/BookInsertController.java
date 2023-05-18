package kr.or.ddit.book.web;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.book.service.BookService;

/*
 * @Controller 어노테이션이 있는 클래스는 스픵이 브라우저의 요청(request)을 받아들이는 컨트롤러라고 인지해서
 * 자바 빈(Java Bean)으로 등록해서 관리한다.
 * 여기서 자바 빈이란 객체를 만들어서 메모리에 올리는 형태를 말한다.
 */



//  @Controller :  해당 함수가 cotroller 역할을 한다고 알려줌
//	@RequestMapping : 해당 컨트롤러에 url 위치를 나타냄

@Controller
@RequestMapping("/book")
public class BookInsertController {
	
	// @Inject : 서버가 run 되면서 미리 만들어서 객체화가 되있는 것을 주입하여 사용한다는 뜻
	// 싱글톤 방식은 대신 해주는 것 
	
	/*
	 * 서비스를 호출하기 위해 BookService를 의존성을 주입한다
	 * 의존성 주입을 통한 결합도 낮추기
	 * 
	 */
	@Inject
	private BookService bookService;
	
	
	/*
	 * @RequestMapping
	 * - 요청 URL을 어떤 메소드가 처리할 지 여부를 결정
	 * 
	 * method 속성은 http 요청 메소드를 의미한다.
	 * 일반적인 웹 페이지 개발에서 GET 메소드는 데이터를 변경하지 않는 경우에, POST 메소드는 데이터가 변경되 경우 사용된다.
	 * 
	 * ModelAndView는 컨트롤러가 반환하 데이터를 담당하는 모델(Model)과 화면을 담당하는 뷰(View)의 경로를 합쳐놓은 객체다.
	 * ModelAndView의 생성자에 문자열 타입 파라미터가 입력되면 뷰의 경로라고 간주한다.
	 * 뷰의 경로를 'book/form'과 같은 형태로 전다하는 이유는 요청(request)에 해당하는 url의 mapping되는 화면의 경로 값을
	 * viewresolver라는 녀석이 제일 먼저 받아 surfix, preffix 속성에 의해서 앞에는 '/WEB-INF/views/'가 붙고
	 * 뒤에는 '.jsp'가 붙어 최종 위치에 해당하는 jsp파일을 찾아준다.
	 * 
	 */
	
	
	// ModelAndView : Spring에서 제공해주는 여러 리턴값 중 하나
	// 화면과 데이터를 담당하는 리턴값 / 데이터를 보여줄 수 있고 뷰 위치도 지정할 수 있음
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public ModelAndView bookForm() {
		// forword 방식  / 스프링 프레임워크가 대신 .jsp 등 앞 뒤 위치를 설정해줌 / servlet-context.xml
		return new ModelAndView("book/form");
		
		// 	<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
//				<beans:property name="prefix" value="/WEB-INF/views/" />
//				<beans:property name="suffix" value=".jsp" />
//			</beans:bean>
		// addObject(key, value) 하면 modelandview 객체 자체가 데이터와 view 모두 담당하기 때문에 이런식으로 사용가능
		// setViewName(viewName) 하면 modelandview 객체 자체가 데이터와 view 모두 담당하기 때문에 이런식으로 사용가능
//		return new ModelAndView("book/form").addObject(attributeName, attributeValue);
//		return new ModelAndView("book/form").setViewName(viewName);
		
		
		// redirect 방식
//		return new ModelAndView("redirect:/book/form.do");
	}
	
	
	/*
	 * 데이터의 변경이 일어나므로 http메소드는 POST방식으로 처리
	 * @RequestParam은 HTTP 파라미터를 map변수에 자동으로 바인딩한다.
	 * Map 타입의 경우는 @RequestParam을 붙여야만 HTTP 파라미터의 값을 MAP안에 바인딩해준다.
	 * 
	 */
	// map으로 받을러면 @RequestParam 선언해야함
	@RequestMapping(value="/form.do", method = RequestMethod.POST)
	public ModelAndView insertBook(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		
		// 서비스 메소드 insertBook을 호출하여 책을 등록한다.
		// 서비스 메소드 insertBook을 통해서 등록하고 결과로 bookId를 리턴 받아온다.
		String bookId = bookService.insertBook(map);
		if(bookId == null) {
			// 데이터 입력이 실패할 경우 다시 데이터를 입력받아야 하므로 생성 화면으로 redirect한다
			// ModelAndView 객체는 .setViewName 메소드를 통해 뷰의 경로를 지정할 수 있다.
			mav.setViewName("redirect:/book/form.do");
			// 뷰의 겨오가 redirect:로 시작하면 스프링은 뷰 파일을 찾아가는게 아니라
			// 웹 페이지의 주소(/form.do)를 찾아간다.
		}else {
			// 데이터 입력이 성공하면 상세 페이지로 이동한다.
			mav.setViewName("redirect:/book/detail.do?bookId=" + bookId);			
		}
		return mav;
	}

}
