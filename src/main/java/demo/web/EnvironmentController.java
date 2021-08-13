package demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentController {

	@Autowired
	private ContextRefresher contextRefresher;

	@GetMapping("/refresh")
	public String refresh() {
		contextRefresher.refresh();
		return "contextRefresher ok!";
	}
}
