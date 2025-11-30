package ie.atu.cloudnative.TextForge_api.Services;

import main.java.ie.atu.forge.Stemmers.Lancaster;
import main.java.ie.atu.forge.Stemmers.Lovins;
import main.java.ie.atu.forge.Stemmers.Porter;
import org.springframework.stereotype.Service;

@Service
public class StemmingService {
    private Porter porter;
    private Lancaster lancaster;
    private Lovins lovins;

    public StemmingService() {
        this.porter = new Porter();
        this.lancaster = new Lancaster();
        this.lovins = new Lovins();
    }

//    public String[] stem(String text, String method) {
//
//    }
}
