package text_sharing_cli;

import org.springframework.http.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.client.RestTemplate;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "textSharingCli", description = "Add or view Text snippets", mixinStandardHelpOptions = true, version = "Text Sharing CLI 1.0")
public class TextSharingCLI implements Callable<String> {


    @Option(names = "-a", description = "Adds new snippet with given text")
    private String textContent;

    @Option(names = "-v", description = "Views a snippet, given its hashedId")
    private String textHashedId;

    public static void main(String... args) throws Exception {
        int exitCode = new picocli.CommandLine(new TextSharingCLI()).execute(args);
        System.exit(exitCode);
    }

    public String call() throws Exception {

        if (textContent != null) {

            String md5Hex = DigestUtils.md5Hex(textContent.replace(" ", "-") + "-" + 0).toUpperCase();
            String textUrl = "http://localhost:4200/text-view/" + md5Hex;
            String requestJson = "{ \"textIdHash\": \"" + md5Hex + "\", " +
                                 "\"textList\": [" +
                                    "{\"textId\": \"0\", \"textContent\": \"" + textContent + "\"}" +
                                 "], \"textUrl\": \"" + textUrl + "\" }";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

            ResponseEntity<String> response = restTemplate.
                    exchange("http://localhost:8080/text-sharing/save", HttpMethod.POST, entity, String.class);
            System.out.println(response.getBody());

        }
        if (textHashedId != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response
                    = restTemplate.getForEntity("http://localhost:8080/text-sharing/" + textHashedId, String.class);
            System.out.println(response.getBody());
        }

        return "success";
    }
}
