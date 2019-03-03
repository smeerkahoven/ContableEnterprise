/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scheduler.ufv;

import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.CambioUfv;
import com.configuracion.entities.Parametros;
import com.configuracion.remote.CambioRemote;
import com.configuracion.remote.ParametrosRemote;
import com.scheduler.dolar.DolarScheduler;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author xeio
 */
@Stateless
public class UFVScheduler implements UFVSchedulerLocal {

    @EJB
    private ParametrosRemote ejbParametros;

    @EJB
    private CambioRemote ejbCambio;

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*/5")
    @Override
    public void checkUFVFactor() {
                Date today = new Date();
        String stoday = DateContable.getDateFormat(today, "yyyy/MM/dd");

        CambioUfv ufvToday;
        try {
            ufvToday = (CambioUfv) ejbCambio.get(new CambioUfv(today));
            if (ufvToday == null) {
                double ufvT = obtenerUFVValor();
                CambioUfv d = new CambioUfv();
                d.setFecha(today);
                d.setValor(new BigDecimal(ufvT));
                
                ejbCambio.insert(d);
            }

        } catch (CRUDException ex) {
            Logger.getLogger(DolarScheduler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private double obtenerUFVValor() {
        System.out.println("obtenerUFVValor Timer event: " + new Date());
        HttpURLConnection connection = null;
        //String path = "https://www.bcb.gob.bo/librerias/indicadores/ufv/ultimo.php";
        //String path = "https://www.bcb.gob.bo/librerias/indicadores/dolar/bolsin.php";
        try {
            Parametros p;
            p = (Parametros) ejbParametros.get(new Parametros(Parametros.URL_CAMBIO_UFV));
            URI uri = new URI(p.getValor());
            URL url = new URL(uri.toURL().toString());
            URLConnection yc = url.openConnection();
            connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                //System.out.println(inputLine);
            }

            in.close();
            //System.out.println(connection.getContent());

            Document doc = Jsoup.parse(content.toString());
            String title = doc.title();
            //String body = doc.body().text();
            //System.out.printf("Body: %s", body);

            Elements e = doc.select("table");

            for (Element table : e) {
                for (Element row : table.select("tr")) {
                    //if (row.hasClass("tablablanca")) {
                    Elements tds = row.select("td");
                    StringTokenizer token = new StringTokenizer(tds.get(0).text());
                    String value = token.nextToken("Bs por").trim();
                    String v = new String(value.replace(",", ".").split(" ")[0]);
                    return Double.parseDouble(v.substring(0,v.length()-2)) ;
                    //return new Double(value.replace(",", ".").trim());
                    //   }
                }
            }

            //System.out.println(doc.getElementsByTag("table"));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
