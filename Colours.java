import java.io.IOException;
import java.net.URI;
import org.json.JSONObject;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Colours {

    public static List<Integer> getColour(){ // generates three random values to be used as RGB
       List<Integer> RGB = new ArrayList<>();
       for (int x = 0; x < 3; x++){
           RGB.add((int) (Math.random() * 255));
       }
       return RGB; // returns an integer list of three values
    }

    public static String[] getColourInfo(String RGB) throws IOException, InterruptedException {
        String API_root = "https://www.thecolorapi.com/id?rgb=rgb";
        String ext = "("+RGB+")";
        String API_URL = API_root + ext;

        var client = HttpClient.newHttpClient();
        var req = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .header("accept","application/json")
                .build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        JSONObject content = new JSONObject(response.body());

        var name = content.get("name");
        JSONObject nameO = (JSONObject) name;
        var hex = content.get("hex");
        JSONObject hexO = (JSONObject) hex;

        String colourShade = nameO.get("value").toString();
        String colourHex = hexO.get("value").toString();
        return new String[]{colourShade,colourHex};
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.initialiseGUI(0,0,0); // original colour is purely black
    }
}
