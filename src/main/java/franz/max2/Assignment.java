package franz.max2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assignment")
public class Assignment {

	@RequestMapping("test")
	public String assignmentTest() {
		return "test";
	}
}
