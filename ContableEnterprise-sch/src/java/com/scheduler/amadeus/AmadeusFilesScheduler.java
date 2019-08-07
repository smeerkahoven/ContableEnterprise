/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scheduler.amadeus;

import com.configuracion.entities.ArchivoBoleto;
import com.configuracion.entities.Parametros;
import com.configuracion.remote.AmadeusFileRemote;
import com.configuracion.remote.CambioRemote;
import com.configuracion.remote.ParametrosRemote;
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
public class AmadeusFilesScheduler {

    @EJB
    private LoggerRemote ejbLogger;

    @EJB
    private AmadeusFileRemote ejbAmadeus;

    @EJB
    private ParametrosRemote ejbParametros;

    // MODIFICAR ESTO EN PRODUCCION
    @Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "7-18", dayOfMonth = "*", year = "*", minute = "*/5", second = "0")
    //@Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*/1", second = "0")
    public void dailyFileChecker() {
        Parametros p;
        try {

            p = (Parametros) ejbParametros.get(new Parametros(Parametros.FOLDER_FILES_AMADEUS));

            System.out.println("files:");
            System.out.println("files:Timer event Files Amadeus: " + new Date());
            ClassLoader loader = this.getClass().getClassLoader();

            System.out.println("files:" + p.getValor());
            File mainFolder = new File(loader.getResource(p.getValor()).getPath());
            if (!mainFolder.exists()) {
                mainFolder.mkdir();
            }

            System.out.println("files: exists:" + mainFolder.getAbsolutePath());
            System.out.println("files:" + p.getValor() + "/backup");
            File backupFolder = new File(loader.getResource(p.getValor() + "/backup").getPath());
            if (!backupFolder.exists()) {
                backupFolder.mkdir();
            }

            for (final File f : mainFolder.listFiles()) {
                System.out.println(f.getName());
                if (f.isFile() && !f.getName().contains(".jar") && !f.getName().contains(".sh")) {
                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("nombreArchivo", f.getName());
                    List l = ejbAmadeus.get("ArchivoBoleto.findByNombreArchivo", ArchivoBoleto.class, parameters);

                    //Si existe ya en BD, se elimina
                    if (!l.isEmpty()) {
                        f.delete();
                    } else {
                        //Si no se registra en la BD
                        String content = readFileContent(f);
                        System.out.println("Content:" + content);
                        saveFile(f.getName(), content);
                        // movemos el archivo a la zona backup
                        if (!backupFolder.exists()) {
                            backupFolder.mkdir();
                        }
                        Path from = Paths.get(f.getAbsolutePath());
                        Path to = Paths.get(backupFolder.getAbsolutePath() + "/" + f.getName());
                        Files.move(from, to);
                    }
                }
            }
            System.out.println("File Amadeus Folder:" + mainFolder.exists());
            System.out.println("File Amadeus Folder:" + mainFolder.getAbsolutePath());
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
                System.out.println("Reading line:" + currentLine);
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
        b.setTipoArchivo(ArchivoBoleto.TipoArchivo.AMADEUS);
        b.setContenido(content);
        b.setFechaInsert(DateContable.getCurrentDate());
        b.setEstado(ArchivoBoleto.Estado.CREADO);

        System.out.println(b);

        ejbAmadeus.insert(b);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
