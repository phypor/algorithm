package org.kd.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pc")
public class PcIndexController {
      
	@RequestMapping("/{childNode}")
	public String index(@PathVariable String childNode){
		return "PC/"+childNode;
	}
}
