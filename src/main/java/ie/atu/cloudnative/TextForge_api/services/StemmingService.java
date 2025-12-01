package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Stemmers.Lancaster;
import main.java.ie.atu.forge.Stemmers.Lovins;
import main.java.ie.atu.forge.Stemmers.Porter;
import org.springframework.stereotype.Service;

@Service
public class StemmingService {
    public String[] porter(String text) {
        return Porter.stem(text.split(" "));
    }

    public String[] lancaster(String text) {
        return Lancaster.stem(text.split(" "));
    }

    public String[] lovins(String text) {
        return Lovins.stem(text.split(" "));
    }
}
