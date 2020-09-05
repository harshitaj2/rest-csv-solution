
package healthcare.carta.interview.restcsv;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RestCsvController {
	private static String DELIMITER = ",";
	@PostMapping("/")
	public Output totalNumber(@RequestParam("data") MultipartFile file, @RequestParam("column") String columnName) throws IOException {
		
		InputStream inputStream = file.getInputStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		Scanner sc = new Scanner(bufferedInputStream);
		String header = sc.nextLine();
		String[] columns = header.split(",");
		int numbersIndex = IntStream.range(0, columns.length)
		
		.filter(i ->  columns[i].equalsIgnoreCase(columnName))
		.findFirst().getAsInt();
		String line = "";
		String[] splittedvalues;
		float data = 0f;
		int lines =0;
		while (sc.hasNextLine()) {
			splittedvalues = sc.nextLine().split(DELIMITER);
			data+=Float.parseFloat(splittedvalues[numbersIndex]);
			lines++;
		}
		if (lines>0)
			data/=lines;
		
		return new Output(data);
		
	}
	

}
