/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scheduler.sabre;

import com.configuracion.entities.ArchivoBoleto;
import com.configuracion.entities.Parametros;
import com.configuracion.remote.CambioRemote;
import com.configuracion.remote.ParametrosRemote;
import com.configuracion.remote.SabreFileRemote;
import com.scheduler.amadeus.AmadeusFilesScheduler;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.LoggerRemote;
import com.seguridad.utils.DateContable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author xeio
 */
@Stateless
@LocalBean
public class SabreFileScheduler {

    @EJB
    private LoggerRemote ejbLogger;

    @EJB
    private CambioRemote ejbCambio;

    @EJB
    private SabreFileRemote ejbSabre;

    @EJB
    private ParametrosRemote ejbParametros;

    //@Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "7-18", dayOfMonth = "*", year = "*", minute = "*/5", second = "0")
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", minute = "*/10", second = "0")
    public void dailyFileChecker() {
        Parametros p;
        try {

            p = (Parametros) ejbParametros.get(new Parametros(Parametros.FOLDER_FILES_SABRE));

            System.out.println("Timer event Files Sabre: " + new Date());
            ClassLoader loader = this.getClass().getClassLoader();

            File mainFolder = new File(loader.getResource(p.getValor()).getPath());
            File backupFolder = new File(loader.getResource(p.getValor() + "/backup").getPath());

            for (final File f : mainFolder.listFiles()) {
                System.out.println(f.getName());
                if (f.isFile()) {
                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("nombreArchivo", f.getName());
                    List l = ejbSabre.get("ArchivoBoleto.findByNombreArchivo", ArchivoBoleto.class, parameters);

                    //Si existe ya en BD, se elimina
                    if (!l.isEmpty()) {
                        f.delete();
                    } else {
                        //Si no se registra en la BD
                        String content = readFileContent(f);
                        saveFile(f.getName(), content);
                        // movemos el archivo a la zona backup
                        Path from = Paths.get(f.getAbsolutePath());
                        if (!backupFolder.exists()) {
                            backupFolder.mkdir();
                        }
                        Path to = Paths.get(backupFolder.getAbsolutePath() + "/" + f.getName());
                        Files.move(from, to);
                    }
                }
            }
            System.out.println("File Sabre Folder:" + mainFolder.exists());
            System.out.println("File Sabre Folder:" + mainFolder.getAbsolutePath());
            //loader.getResourceAsStream("../../resources/myFile.txt")
        } catch (Exception ex) {
            Logger.getLogger(AmadeusFilesScheduler.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private String readFileContent(File f) {

        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                builder.append(currentLine).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    private void saveFile(String fileName, String content) throws CRUDException {
        ArchivoBoleto b = new ArchivoBoleto();
        b.setNombreArchivo(fileName);
        b.setTipoArchivo(ArchivoBoleto.TipoArchivo.SABRE);
        b.setContenido(content);
        b.setFechaInsert(DateContable.getCurrentDate());
        b.setEstado(ArchivoBoleto.Estado.CREADO);

        ejbSabre.insert(b);
    }
}
