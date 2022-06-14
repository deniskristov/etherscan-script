package com.etherscan.script;

import com.etherscan.script.utils.UrlUtils;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.util.Assert;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ScriptApplicationTests {

//	@Test
//	void htmlUnits() throws IOException, InterruptedException
//	{
//		WebClient webClient = new WebClient();
//		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//		webClient.getOptions().setThrowExceptionOnScriptError(false);
//		HtmlPage page = webClient.getPage("https://etherscan.io/readContract?m=normal&a=0x34d85c9CDeB23FA97cb08333b511ac86E1C4E258&v=0x34d85c9CDeB23FA97cb08333b511ac86E1C4E258");
//
//		HtmlAnchor anchorByText = page.getAnchorByHref("#");
//		anchorByText.click();
//
//		HtmlInput input = page.getElementByName("input_55");
//		input.setValueAttribute("10");
//		HtmlButton button = (HtmlButton)page.getElementById("btn_55");
//		HtmlPage click = button.click();
//
//		Thread.sleep(5000);
//		DomElement myanswer = click.getElementById("myanswer_55");
//		System.out.println(myanswer.getTextContent());
//
//	}


	@Test
	void extractUrls() {
		String answer = "[ tokenURI(uint256) method Response ]\n" +
			"    string :  https://api.otherside.xyz/lands/15";
		Assertions.assertEquals("https://api.otherside.xyz/lands/15", UrlUtils.extract(answer).get(0));
	}
}
