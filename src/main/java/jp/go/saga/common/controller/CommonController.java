package jp.go.saga.common.controller;

import jp.go.saga.common.message.MessageProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bsmedia.system.config.ApplicationProperty;

@Controller
public class CommonController {

	@Autowired
	MessageProperty messageProperty;

	/*
	 * Message
	 */
	@RequestMapping("/common/getMessageProperty.do")
	public @ResponseBody String getMessageProperty(@RequestParam("key") String key){
		return messageProperty.getMessage(key);
	}
}
