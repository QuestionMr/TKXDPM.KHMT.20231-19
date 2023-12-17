import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import subsystem.interbank.InterbankSubsystemController;
import utils.Configs;

import javax.swing.text.html.StyleSheet;


public class VNPayTest {
	public static void main(String[] args) {
		InterbankSubsystemController itb = new InterbankSubsystemController();
		String connectResponse = itb.connectToVNPay(12000000, "yes");
		System.out.println(connectResponse);
		try {
			java.awt.Desktop.getDesktop().browse(new URI(Configs.PROCESS_TRANSACTION_URL + "?" + connectResponse));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		final JFrame frame=new JFrame();
//		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		frame.setSize(620,440);
//		final JFXPanel fxpanel=new JFXPanel();
//		frame.add(fxpanel);

//		Platform.runLater(new Runnable() {
//		@Override
//		public void run()
//		    {
//		    WebEngine engine;
//		    WebView wv=new WebView();
//		    engine=wv.getEngine();
//		    fxpanel.setScene(new Scene(wv));
//		    engine.load(Configs.PROCESS_TRANSACTION_URL + "?" + connectResponse);
//		    }
//		    });
//		frame.setVisible(true);
		}
}
