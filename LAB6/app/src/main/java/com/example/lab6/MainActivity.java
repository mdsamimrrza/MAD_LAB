package com.example.lab6;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        TextView xml = findViewById(R.id.xmlData);
        TextView json = findViewById(R.id.jsonData);

        findViewById(R.id.btnParserXML).setOnClickListener(v -> {
            try (InputStream is = getResources().openRawResource(R.raw.city_data_xml)) {
                Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
                NodeList n = d.getDocumentElement().getChildNodes();
                StringBuilder out = new StringBuilder();
                for (int i = 0; i < n.getLength(); i++) {
                    Node node = n.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE)
                        out.append(node.getNodeName()).append(": ").append(node.getTextContent().trim()).append("\n");
                }
                xml.setText(out.toString());
            } catch (Exception e) {
                xml.setText("XML Error");
            }
        });

        findViewById(R.id.btnParserJSON).setOnClickListener(v -> {
            try (InputStream is = getResources().openRawResource(R.raw.city_data_json)) {
                byte[] buf = new byte[is.available()];
                is.read(buf);
                JSONObject o = new JSONObject(new String(buf, StandardCharsets.UTF_8));
                json.setText("name: " + o.getString("name") + "\n" +
                        "latitude: " + o.getDouble("latitude") + "\n" +
                        "longitude: " + o.getDouble("longitude") + "\n" +
                        "temperature: " + o.getDouble("temperature") + "\n" +
                        "humidity: " + o.getInt("humidity"));
            } catch (Exception e) {
                json.setText("JSON Error");
            }
        });
    }
}
