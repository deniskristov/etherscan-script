package com.etherscan.script;

import com.etherscan.script.utils.UrlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
	void extractUriHttp() {
		String answer = "[ tokenURI(uint256) method Response ]\n" +
			"    string :  https://api.otherside.xyz/lands/15";
		Assertions.assertEquals("https://api.otherside.xyz/lands/15", UrlUtils.extractUri(answer).get());
	}

	@Test
	void extractUriNonHttp() {
		String answer = " string\n" +
            "\n" +
            "[ tokenURI(uint256) method Response ]\n" +
            "    string : ipfs://QmfZNoPiSfyYjruksDzTn8VSDpYsCjUkYXGpeeYCtpSTfp/12";
		Assertions.assertEquals("ipfs://QmfZNoPiSfyYjruksDzTn8VSDpYsCjUkYXGpeeYCtpSTfp/12", UrlUtils.extractUri(answer).get());
	}

	@Test
	void extractUriErrorPattern() {
		String answer = " string\n" +
			"\n" +
			"[ tokenURI(uint256) method Response ]\n" +
			"    : ipfs://QmfZNoPiSfyYjruksDzTn8VSDpYsCjUkYXGpeeYCtpSTfp/12";
		Assertions.assertTrue(UrlUtils.extractUri(answer).isEmpty());
	}

	@Test
	void extractUriError() {
		String answer = "Error: Invalid JSON RPC response: \"\"";
		Assertions.assertTrue(UrlUtils.extractUri(answer).isEmpty());
	}

	@Test
	void cutNumbers()
	{
		Assertions.assertEquals("https://api.gossamer.world/api/nft/",
			UrlUtils.cutNumbers("https://api.gossamer.world/api/nft/30"));
	}


	void urlParserTest() throws IOException, InterruptedException
	{
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest.Builder request = HttpRequest.newBuilder()
			.GET()
			.uri(URI.create("https://api.gossamer.world/api/nft/30"));
		HttpResponse<String> response = httpClient.send(request.build(), HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == HttpURLConnection.HTTP_OK)
		{
			if (response.body().contains("trait_type"))
			{
				System.out.println("Found");
			}
			else
			{
				System.out.println("Not found");
			}
		}
		else
		{
			System.out.println("HTTP error: " + response.statusCode());
		}
	}
}
