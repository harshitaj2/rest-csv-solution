package healthcare.carta.interview.restcsv;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired

	private ResourceLoader resourceLoader;

	@Test
	public void getReturnMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("405");
	}
	@Test
	public void postreturn() throws Exception{
		Resource inputStream =  resourceLoader.getResource("classpath:data.csv");
		MultiValueMap<String,Object> parameters = new LinkedMultiValueMap<String,Object>();
		parameters.add("data", inputStream);
		parameters.add("column", "number");
	    HttpHeaders headers = new HttpHeaders();

	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(parameters, headers);

		Output ret = restTemplate.postForObject("http://localhost:" + port + "/", request, Output.class);
		assertThat(ret).isNotNull();
	}

}
