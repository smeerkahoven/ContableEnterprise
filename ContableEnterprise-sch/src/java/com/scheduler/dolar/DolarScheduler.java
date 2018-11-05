/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scheduler.dolar;

import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.Parametros;
import com.configuracion.remote.CambioRemote;
import com.configuracion.remote.ParametrosRemote;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author xeio
 */
@Stateless
public class DolarScheduler implements DolarSchedulerLocal {

    @EJB
    private ParametrosRemote ejbParametros;

    @EJB
    private CambioRemote ejbCambio;

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*/5")
    @Override
    public void checkDolarFactor() {
        System.out.println("checkDolarFactor Timer event: " + new Date());

        Date today = new Date();
        String stoday = DateContable.getDateFormat(today, "yyyy/MM/dd");

        CambioDolar dolarToday;
        try {
            dolarToday = (CambioDolar) ejbCambio.get(new CambioDolar(today));
            if (dolarToday == null) {
                double dolarT = obtenerValorDolar();
                CambioDolar d = new CambioDolar();
                d.setFecha(today);
                d.setValor(new BigDecimal(dolarT));

                ejbCambio.insert(d);
            }

        } catch (CRUDException ex) {
            Logger.getLogger(DolarScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private double obtenerValorDolar() {
        HttpsURLConnection connection = null;
        //String path = "https://www.bcb.gob.bo/librerias/indicadores/ufv/ultimo.php";
        //String path = "https://www.bcb.gob.bo/librerias/indicadores/dolar/bolsin.php";

        Parametros p;
        try {
            p = (Parametros) ejbParametros.get(new Parametros(Parametros.URL_CAMBIO_DOLAR));

            URI uri = new URI(p.getValor());
            URL url = new URL(uri.toURL().toString());
            URLConnection yc = url.openConnection();
            connection = (HttpsURLConnection) url.openConnection();
            print_https_cert(connection);
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                //System.out.println(inputLine);
            }
             
            in.close();

            Document doc = Jsoup.parse(content.toString());
            String title = doc.title();
            //String body = doc.body().text();
            //System.out.printf("Body: %s", body);

            Elements e = doc.select("table");

            for (Element table : e) {
                for (Element row : table.select("tr")) {
                    if (row.hasClass("tablablanca")) {
                        Elements tds = row.select("td");
                        StringTokenizer token = new StringTokenizer(tds.get(1).text());
                        String value = token.nextToken("Bs ");
                        System.out.println(value);
                        return new Double(value.replace(",", ".").trim());

                    }
                }
            }
        } catch (CRUDException | URISyntaxException | IOException ex) {
            Logger.getLogger(DolarScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;

    }

    private void print_https_cert(HttpsURLConnection con) {

        if (con != null) {

            try {

                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for (Certificate cert : certs) {
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
