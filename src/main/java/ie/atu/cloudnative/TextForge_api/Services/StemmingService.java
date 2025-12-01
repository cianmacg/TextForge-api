package ie.atu.cloudnative.TextForge_api.Services;

import main.java.ie.atu.forge.Stemmers.Lancaster;
import main.java.ie.atu.forge.Stemmers.Lovins;
import main.java.ie.atu.forge.Stemmers.Porter;
import org.springframework.stereotype.Service;

@Service
public class StemmingService {
    public String[] stem(String text, String method) {
        String[] words = text.split(" ");

        return switch (method) {
            case "porter" -> Porter.stem(words);
            case "lancaster" -> Lancaster.stem(words);
            case "lovins" -> Lovins.stem(words);
            default -> null;
        };
    }
}
