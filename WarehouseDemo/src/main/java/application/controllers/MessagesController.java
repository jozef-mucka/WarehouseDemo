package application.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.io.Resource;

@Controller
public class MessagesController {

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/messages")
	@ResponseBody
	public Map<String, String> messages(Locale locale) {
		Resource resource = new ClassPathResource("messages.properties");
		Properties properties = new Properties();
		try {
			properties.load(resource.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Convert to a map with resolved values
		Map<String, String> messages = new HashMap<>();
		for (String key : properties.stringPropertyNames()) {
			String value = messageSource.getMessage(key, null, locale);
			messages.put(key, value);
		}

		return messages;
	}
}
