package ptit.cuonghq.walltag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ptit.cuonghq.walltag.utils.FileStorageProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class WalltagApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalltagApplication.class, args);
	}
}
