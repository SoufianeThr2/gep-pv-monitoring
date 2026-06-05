package com.gep.monitoring.service;

import com.gep.monitoring.entity.*;
import com.gep.monitoring.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service d'initialisation des données au démarrage de l'application.
 *
 * Implémente CommandLineRunner, ce qui signifie que la méthode run()
 * est exécutée automatiquement par Spring Boot juste après le démarrage.
 *
 * Responsabilités :
 * 1. Créer un compte administrateur par défaut (si inexistant)
 * 2. Importer les données CSV dans la base de données PostgreSQL (si vide)
 *
 * Ordre d'import important (contraintes de clés étrangères) :
 * modules → inverters → pvsystems → ac_production → dc_production
 */
@Service
public class CsvDataLoaderService implements CommandLineRunner {

    private final PvSystemRepository pvSystemRepo;
    private final ModuleSpecRepository moduleRepo;
    private final InverterRepository inverterRepo;
    private final DcProductionRepository dcRepo;
    private final AcProductionRepository acRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public CsvDataLoaderService(PvSystemRepository pvSystemRepo,
                                ModuleSpecRepository moduleRepo,
                                InverterRepository inverterRepo,
                                DcProductionRepository dcRepo,
                                AcProductionRepository acRepo,
                                UserRepository userRepo,
                                PasswordEncoder passwordEncoder) {
        this.pvSystemRepo = pvSystemRepo;
        this.moduleRepo = moduleRepo;
        this.inverterRepo = inverterRepo;
        this.dcRepo = dcRepo;
        this.acRepo = acRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Point d'entrée principal — appelé automatiquement au démarrage par Spring Boot.
     */
    @Override
    public void run(String... args) throws Exception {
        createDefaultAdminUser();
        importCsvDataIfEmpty();
    }

    /**
     * Crée l'utilisateur administrateur par défaut si il n'existe pas encore en base.
     * Le mot de passe est haché avec BCrypt avant d'être stocké.
     */
    private void createDefaultAdminUser() {
        // Utilisation de Optional (isPresent()) au lieu de == null
        // car UserRepository retourne maintenant Optional<User>
        if (userRepo.findByEmail("admin@gep.ma").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@gep.ma");
            admin.setPassword(passwordEncoder.encode("admin123"));
            userRepo.save(admin);
            System.out.println("Utilisateur admin@gep.ma créé avec succès.");
        }
    }

    /**
     * Lance l'import de tous les CSV uniquement si la base de données est vide.
     * Ce mécanisme évite de dupliquer les données à chaque redémarrage du serveur.
     */
    private void importCsvDataIfEmpty() {
        if (pvSystemRepo.count() == 0) {
            System.out.println("Base de données vide : Début de l'importation des CSV...");
            loadModules();
            loadInverters();
            loadPvSystems();
            loadAcProduction();
            loadDcProduction();
            System.out.println("Importation terminée avec succès !");
        } else {
            System.out.println("Les données existent déjà dans la base. Importation ignorée.");
        }
    }

    /**
     * Charge le fichier modules.csv et insère les données en base.
     * Chaque ligne = un type de module photovoltaïque.
     */
    private void loadModules() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("modules.csv").getInputStream()))) {
            br.readLine(); // Ignorer la ligne d'en-têtes
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                ModuleSpec module = new ModuleSpec();
                module.setModuleId(data[0]);
                module.setBrand(data[1]);
                module.setModel(data[2]);
                module.setTechnology(data[3]);
                module.setPowerWc(Integer.parseInt(data[4]));
                module.setNbPerString(Integer.parseInt(data[5]));
                module.setVocV(Double.parseDouble(data[6]));
                module.setIscA(Double.parseDouble(data[7]));
                module.setTempCoeffPmax(Double.parseDouble(data[8]));
                moduleRepo.save(module);
            }
            System.out.println("Importation Modules terminée.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import Modules: " + e.getMessage());
        }
    }

    /**
     * Charge le fichier inverters.csv et insère les données en base.
     * Chaque ligne = un onduleur lié à un système PV.
     */
    private void loadInverters() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("inverters.csv").getInputStream()))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Inverter inv = new Inverter();
                inv.setInverterId(data[0]);
                inv.setBrand(data[1]);
                inv.setModel(data[2]);
                inv.setPowerKwAc(Double.parseDouble(data[3]));
                inv.setNbMppt(Integer.parseInt(data[4]));
                inv.setMaxInputVoltageV(Double.parseDouble(data[5]));
                inv.setMaxInputCurrentA(Double.parseDouble(data[6]));
                inv.setEfficiencyPct(Double.parseDouble(data[7]));
                inv.setSerialNumber(data[8]);
                inv.setSystemId(data[9]);
                inverterRepo.save(inv);
            }
            System.out.println("Importation Onduleurs terminée.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import Onduleurs: " + e.getMessage());
        }
    }

    /**
     * Charge le fichier pvsystems.csv et insère les données en base.
     * Chaque ligne = un système PV complet avec ses coordonnées et métadonnées.
     */
    private void loadPvSystems() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("pvsystems.csv").getInputStream()))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                PvSystem sys = new PvSystem();
                sys.setSystemId(data[0]);
                sys.setSystemName(data[1]);
                sys.setLatitude(Double.parseDouble(data[2]));
                sys.setLongitude(Double.parseDouble(data[3]));
                sys.setTotalCapacityKwc(Double.parseDouble(data[4]));
                sys.setCommissioningDate(LocalDate.parse(data[5]));
                sys.setOrientation(data[6]);
                sys.setTiltAngle(Integer.parseInt(data[7]));
                sys.setNbStrings(Integer.parseInt(data[8]));
                sys.setModuleId(data[9]);
                sys.setInverterId(data[10]);
                pvSystemRepo.save(sys);
            }
            System.out.println("Importation Systèmes PV terminée.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import PvSystems: " + e.getMessage());
        }
    }

    /**
     * Charge le fichier ac_production.csv et insère les données en base.
     * Utilise saveAll() pour une insertion groupée (batch) — beaucoup plus rapide
     * qu'un save() individuel pour chaque ligne.
     */
    private void loadAcProduction() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("ac_production.csv").getInputStream()))) {
            br.readLine();
            String line;
            List<AcProduction> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                AcProduction ac = new AcProduction();
                ac.setTimestamp(LocalDateTime.parse(data[0]));
                ac.setSystemId(data[1]);
                ac.setAcPowerKw(Double.parseDouble(data[2]));
                ac.setAcEnergyKwh(Double.parseDouble(data[3]));
                ac.setAcVoltageV(Double.parseDouble(data[4]));
                ac.setAcFrequencyHz(Double.parseDouble(data[5]));
                ac.setPowerFactor(Double.parseDouble(data[6]));
                list.add(ac);
            }
            acRepo.saveAll(list); // Insertion groupée = batch insert
            System.out.println("Importation AC terminée : " + list.size() + " lignes chargées.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'import AC: " + e.getMessage());
        }
    }

    /**
     * Charge le fichier dc_production.csv et insère les données en base.
     * Utilise saveAll() pour une insertion groupée (batch).
     */
    private void loadDcProduction() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("dc_production.csv").getInputStream()))) {
            br.readLine();
            String line;
            List<DcProduction> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                DcProduction dc = new DcProduction();
                dc.setTimestamp(LocalDateTime.parse(data[0]));
                dc.setSystemId(data[1]);
                dc.setDcPowerKw(Double.parseDouble(data[2]));
                dc.setDcVoltageV(Double.parseDouble(data[3]));
                dc.setDcCurrentA(Double.parseDouble(data[4]));
                dc.setIrradianceWm2(Double.parseDouble(data[5]));
                list.add(dc);
            }
            dcRepo.saveAll(list); // Insertion groupée = batch insert
            System.out.println("Importation DC terminée : " + list.size() + " lignes chargées.");
        } catch (Exception e) {
            System.err.println("Erreur fatale lors de l'import DC: " + e.getMessage());
        }
    }
}